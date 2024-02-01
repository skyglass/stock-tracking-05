package net.greeta.stock.catalog.application.integrationevents.eventhandling;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.integrationevents.IntegrationEventPublisher;
import net.greeta.stock.catalog.application.integrationevents.events.*;
import net.greeta.stock.catalog.config.KafkaTopics;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItem;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemRepository;
import net.greeta.stock.common.domain.dto.catalog.AddStockCommand;
import net.greeta.stock.common.domain.dto.catalog.RemoveStockCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToAwaitingValidationIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationIntegrationEventHandler.class);

  private final CatalogItemRepository catalogItemRepository;
  private final IntegrationEventPublisher integrationEventService;
  private final KafkaTopics kafkaTopics;
  private final CatalogCommandBus commandBus;

  @KafkaListener(
      groupId = "${app.kafka.group.ordersWaitingValidation}",
      topics = "${spring.kafka.consumer.topic.ordersWaitingForValidation}"
  )
  @Transactional
  public void handle(OrderStatusChangedToAwaitingValidationIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    var confirmedOrderStockItems = new ArrayList<ConfirmedOrderStockItem>();

    event.getOrderStockItems().forEach(orderStockItem -> catalogItemRepository
        .load(orderStockItem.getProductId())
        .ifPresent(catalogItem ->
            confirmedOrderStockItems.add(createConfirmedOrderStockItem(catalogItem, orderStockItem)))
    );

    boolean allItemsAvailable = allItemsAvailable(confirmedOrderStockItems);
    if (allItemsAvailable) {
      var processedStockItems = new ArrayList<OrderStockItem>();
      allItemsAvailable = processItems(processedStockItems, event);
      if (allItemsAvailable) {
        integrationEventService.publish(
                kafkaTopics.getOrderStockConfirmed(),
                new OrderStockConfirmedIntegrationEvent(event.getOrderId()));
      } else {
        rollbackProcessedItems(processedStockItems);
      }
    }
    if (!allItemsAvailable) {
      integrationEventService.publish(kafkaTopics.getOrderStockRejected(),
          new OrderStockRejectedIntegrationEvent(event.getOrderId(), confirmedOrderStockItems));
    }

  }

  private boolean processItems(List<OrderStockItem> processedStockItems, OrderStatusChangedToAwaitingValidationIntegrationEvent event) {
    for (OrderStockItem orderItem: event.getOrderStockItems()) {
      RemoveStockCommand command = new RemoveStockCommand(orderItem.getProductId(), orderItem.getUnits());
      try {
        commandBus.execute(command);
        processedStockItems.add(orderItem);
      } catch (Exception e) {
        return false;
      }
    };
    return true;
  }

  private boolean rollbackProcessedItems(List<OrderStockItem> processedStockItems) {
    for (OrderStockItem orderItem: processedStockItems) {
      AddStockCommand command = new AddStockCommand(orderItem.getProductId(), orderItem.getUnits());
      try {
        commandBus.execute(command);
      } catch (Exception e) {
        //TODO: implement retry and DLQ
      }
    };
    return true;
  }

  private ConfirmedOrderStockItem createConfirmedOrderStockItem(CatalogItem catalogItem, OrderStockItem orderStockItem) {
    return new ConfirmedOrderStockItem(catalogItem.getId(), hasStock(catalogItem, orderStockItem));
  }

  private boolean hasStock(CatalogItem catalogItem, OrderStockItem orderStockItem) {
    return catalogItem.getAvailableStock().getValue() >= orderStockItem.getUnits();
  }

  private boolean allItemsAvailable(List<ConfirmedOrderStockItem> confirmedOrderStockItems) {
    return confirmedOrderStockItems.stream().allMatch(ConfirmedOrderStockItem::getHasStock);
  }

}
