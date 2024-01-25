package net.greeta.stock.ordering.api.application.domaineventhandlers.ordershipped;

import net.greeta.stock.ordering.api.application.domaineventhandlers.DomainEventHandler;
import net.greeta.stock.ordering.api.application.integrationevents.events.OrderStatusChangedToShippedIntegrationEvent;
import net.greeta.stock.ordering.api.application.services.OrderApplicationService;
import net.greeta.stock.ordering.config.KafkaTopics;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderStatus;
import net.greeta.stock.ordering.domain.events.OrderShippedDomainEvent;
import net.greeta.stock.ordering.shared.EventHandler;
import net.greeta.stock.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

@EventHandler
@RequiredArgsConstructor
public class OrderShippedDomainEventHandler implements DomainEventHandler<OrderShippedDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderShippedDomainEventHandler.class);

  private final OrderApplicationService orderApplicationService;
  private final IntegrationEventLogService integrationEventLogService;
  private final KafkaTopics topics;

  @EventListener
  public void handle(OrderShippedDomainEvent orderShippedDomainEvent) {
    logger.info(
        "Order with Id: {} has been successfully updated to status {}",
        orderShippedDomainEvent.order().getId(),
        OrderStatus.Shipped
    );

    var order = orderApplicationService.findOrder(orderShippedDomainEvent.order().getId());
    var buyer = orderApplicationService.findBuyerFor(order);

    var orderStatusChangedToCancelledIntegrationEvent = new OrderStatusChangedToShippedIntegrationEvent(
        order.getId().getUuid(), order.getOrderStatus().getStatus(), buyer.getBuyerName().getName());
    integrationEventLogService.saveEvent(orderStatusChangedToCancelledIntegrationEvent, topics.getShippedOrders());
  }
}
