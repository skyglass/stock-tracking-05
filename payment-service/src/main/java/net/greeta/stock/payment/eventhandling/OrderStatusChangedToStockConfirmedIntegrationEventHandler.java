package net.greeta.stock.payment.eventhandling;

import net.greeta.stock.shared.eventhandling.EventBus;
import net.greeta.stock.payment.events.OrderPaymentFailedIntegrationEvent;
import net.greeta.stock.payment.events.OrderPaymentSucceededIntegrationEvent;
import net.greeta.stock.payment.events.OrderStatusChangedToStockConfirmedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusChangedToStockConfirmedIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToStockConfirmedIntegrationEventHandler.class);

  private final EventBus paymentStatusEventBus;

  @KafkaListener(
      groupId = "${app.kafka.group.stockConfirmed}",
      topics = "${spring.kafka.consumer.topic.stockConfirmed}"
  )
  public void handle(OrderStatusChangedToStockConfirmedIntegrationEvent event) {
    logger.info("Handling integration event: {} - ({})", event.getId(), event.getClass().getSimpleName());

      paymentStatusEventBus.publish(new OrderPaymentSucceededIntegrationEvent(event.getOrderId()));
      //paymentStatusEventBus.publish(new OrderPaymentFailedIntegrationEvent(event.getOrderId()));
  }
}
