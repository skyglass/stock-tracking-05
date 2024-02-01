package net.greeta.stock.catalog.application.integrationevents.eventhandling;

import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.integrationevents.events.OrderStatusChangedToPaidIntegrationEvent;
import net.greeta.stock.catalog.application.integrationevents.events.OrderStockItem;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemRepository;
import net.greeta.stock.catalog.domain.catalogitem.Units;
import lombok.AllArgsConstructor;
import net.greeta.stock.common.domain.dto.catalog.RemoveStockCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderStatusChangedToPaidIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationIntegrationEventHandler.class);

  private final CatalogItemRepository catalogItemRepository;

  private final CatalogCommandBus commandBus;

  @KafkaListener(groupId = "${app.kafka.group.paidOrders}", topics = "${spring.kafka.consumer.topic.paidOrders}")
  public void handle(OrderStatusChangedToPaidIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    for (OrderStockItem orderItem: event.getOrderStockItems()) {
      RemoveStockCommand command = new RemoveStockCommand(orderItem.getProductId(), orderItem.getUnits());
      final var response = commandBus.execute(command);
      logger.info("RemoveStockCommand for product {} with version {}", response.getProductId(), response.getVersion());
    };
  }
}
