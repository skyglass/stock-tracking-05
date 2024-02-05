package net.greeta.stock.catalog.application.commands.confirmstockorder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.application.integrationevents.IntegrationEventPublisher;
import net.greeta.stock.catalog.application.integrationevents.events.OrderStockConfirmedIntegrationEvent;
import net.greeta.stock.catalog.config.KafkaTopics;
import net.greeta.stock.catalog.domain.stockorder.StockOrderAggregate;
import net.greeta.stock.catalog.domain.stockorder.StockOrderAggregateRepository;
import net.greeta.stock.common.domain.dto.catalog.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Aggregate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class ConfirmStockOrderItemCommandHandler implements CatalogCommandHandler<StockOrderResponse, ConfirmStockOrderItemCommand> {
  private final StockOrderAggregateRepository catalogOrderRepository;
  private final IntegrationEventPublisher integrationEventService;
  private final KafkaTopics kafkaTopics;
  private final CatalogCommandBus commandBus;

  @CommandHandler
  public StockOrderResponse handle(ConfirmStockOrderItemCommand command) {
    log.info("ConfirmStockOrderItemCommand started for order {} and product {} with quantity {}", command.orderId(), command.productId(), command.quantity());
    final var stockOrderAggregate = catalogOrderRepository.loadAggregate(command.orderId());
    if (isAlreadyHandled(command.productId(), stockOrderAggregate)) {
      log.info("ConfirmStockOrderItemCommand for order {} has already been handled for product {} with quantity {}",
              command.orderId(), command.productId(), command.quantity());
      return stockOrderResponse(command, stockOrderAggregate);
    }

    RemoveStockCommand removeStockCommand = new RemoveStockCommand(command.orderId(), command.productId(), command.quantity(), command.stockOrderItems());
    commandBus.execute(removeStockCommand);

    final var confirmedStockItem = createConfirmedStockOrderItem(command.productId());
    stockOrderAggregate.execute(c -> c.confirmStockOrderItem(confirmedStockItem));
    var next = getNext(command.productId(), command.stockOrderItems());
    if (next == null) {
      integrationEventService.publish(
              kafkaTopics.getOrderStockConfirmed(),
              new OrderStockConfirmedIntegrationEvent(command.orderId().toString()));
    } else {
      ConfirmStockOrderItemCommand nextCommand = new ConfirmStockOrderItemCommand(
              command.orderId(), next.getProductId(),
              next.getUnits(),
              command.stockOrderItems());
      log.info("ConfirmStockOrderItemCommand started for order {} and product {} with quantity {}", command.orderId(), next.getProductId(), next.getUnits());
      commandBus.execute(nextCommand);
    }

    return stockOrderResponse(command, stockOrderAggregate);
  }

  private StockOrderResponse stockOrderResponse(ConfirmStockOrderItemCommand command,
                                                Aggregate<StockOrderAggregate> stockOrderAggregate) {
    return StockOrderResponse.builder()
            .orderId(command.orderId())
            .version(stockOrderAggregate.version())
            .build();
  }

  private StockOrderItem getNext(UUID productId, List<StockOrderItem> items) {
    for (int i = 0; i < items.size(); i++) {
      var item = items.get(i);
      if (Objects.equals(productId, item.getProductId())) {
        return i < items.size() - 1 ? items.get(i + 1) : null;
      }
    }
    //should never happen!
    throw new RuntimeException("RemoveStockCommand contains unknown productId: " + productId + ". Please, fix the bug!");
  }

  private ConfirmedStockOrderItem createConfirmedStockOrderItem(UUID productId) {
    return new ConfirmedStockOrderItem(productId, true);
  }

  private boolean isAlreadyHandled(UUID productId, Aggregate<StockOrderAggregate> stockOrderAggregate) {
    return stockOrderAggregate
            .invoke(StockOrderAggregate::getConfirmedStockOrderItems)
            .stream().map(ConfirmedStockOrderItem::getProductId)
            .anyMatch(pi -> Objects.equals(productId, pi));
  }
}
