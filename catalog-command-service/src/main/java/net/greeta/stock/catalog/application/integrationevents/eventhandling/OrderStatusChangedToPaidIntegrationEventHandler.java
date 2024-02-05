package net.greeta.stock.catalog.application.integrationevents.eventhandling;

import lombok.AllArgsConstructor;
import net.greeta.stock.catalog.application.integrationevents.events.OrderStatusChangedToPaidIntegrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderStatusChangedToPaidIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationIntegrationEventHandler.class);

  @KafkaListener(groupId = "${app.kafka.group.paidOrders}", topics = "${spring.kafka.consumer.topic.paidOrders}")
  public void handle(OrderStatusChangedToPaidIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
    logger.info("OrderStatusChangedToPaidIntegrationEvent handled for order {} with status {}", event.getOrderId(), event.getOrderStatus());
  }
}
