package net.greeta.stock.catalog.application.commands.removestock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.application.integrationevents.IntegrationEventPublisher;
import net.greeta.stock.catalog.application.integrationevents.events.OrderStockConfirmedIntegrationEvent;
import net.greeta.stock.catalog.application.integrationevents.events.OrderStockRejectedIntegrationEvent;
import net.greeta.stock.catalog.config.KafkaTopics;
import net.greeta.stock.catalog.domain.base.BusinessRuleBrokenException;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItem;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemRepository;
import net.greeta.stock.catalog.domain.catalogitem.Units;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.catalog.ConfirmedOrderStockItem;
import net.greeta.stock.common.domain.dto.catalog.OrderStockItem;
import net.greeta.stock.common.domain.dto.catalog.RemoveStockCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Aggregate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
@Slf4j
public class RemoveStockCommandHandler implements CatalogCommandHandler<CatalogItemResponse, RemoveStockCommand> {
  private final CatalogItemRepository catalogItemRepository;
  private final IntegrationEventPublisher integrationEventService;
  private final KafkaTopics kafkaTopics;
  private final CatalogCommandBus commandBus;

  @CommandHandler
  public CatalogItemResponse handle(RemoveStockCommand command) {
    final var catalogItem = catalogItemRepository.loadAggregate(command.productId());
    if (notEnoughStock(catalogItem, command.productId(), command.quantity())) {
      integrationEventService.publish(kafkaTopics.getOrderStockRejected(),
              new OrderStockRejectedIntegrationEvent(command.orderId()));
    } else {
      final var confirmedStockItem = createConfirmedOrderStockItem(command.productId());
      catalogItem.execute(c -> c.removeStock(confirmedStockItem, Units.of(command.quantity())));
      var next = getNext(command.productId(), command.orderStockItems());
      if (next == null) {
        integrationEventService.publish(
                kafkaTopics.getOrderStockConfirmed(),
                new OrderStockConfirmedIntegrationEvent(command.orderId()));
      } else {
        RemoveStockCommand nextCommand = new RemoveStockCommand(
                command.orderId(), next.getProductId(),
                next.getUnits(),
                command.orderStockItems());
        log.info("RemoveStockCommand started for product {} with quantity {}", next.getProductId(), next.getUnits());
        commandBus.execute(nextCommand);
      }
    }

    return CatalogItemResponse.builder()
        .productId(command.productId())
        .version(catalogItem.version())
        .build();
  }

  private OrderStockItem getNext(UUID productId, List<OrderStockItem> items) {
    for (int i = 0; i < items.size(); i++) {
      var item = items.get(i);
      if (Objects.equals(productId, item.getProductId())) {
        return i < items.size() - 1 ? items.get(i + 1) : null;
      }
    }
    return null;
  }

  private ConfirmedOrderStockItem createConfirmedOrderStockItem(UUID productId) {
    return new ConfirmedOrderStockItem(productId, true);
  }

  private boolean notEnoughStock(Aggregate<CatalogItem> catalogItemAggregate, UUID productId, Integer requestedQuantity) {
    Integer availableStock = catalogItemAggregate.invoke(Function.identity()).getAvailableStock().getValue();
    log.info("Handling hasStock method for productId {}: available - {}, requested - {}", productId, availableStock, requestedQuantity);
    return availableStock < requestedQuantity;
  }
}
