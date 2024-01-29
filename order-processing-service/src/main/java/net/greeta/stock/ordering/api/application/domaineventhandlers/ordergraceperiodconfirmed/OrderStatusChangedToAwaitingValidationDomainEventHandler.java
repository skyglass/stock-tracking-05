package net.greeta.stock.ordering.api.application.domaineventhandlers.ordergraceperiodconfirmed;

import net.greeta.stock.ordering.api.application.domaineventhandlers.DomainEventHandler;
import net.greeta.stock.ordering.api.application.integrationevents.events.OrderStatusChangedToAwaitingValidationIntegrationEvent;
import net.greeta.stock.ordering.api.application.integrationevents.events.models.OrderStockItem;
import net.greeta.stock.ordering.api.application.services.OrderApplicationService;
import net.greeta.stock.ordering.config.KafkaTopics;
import net.greeta.stock.common.domain.dto.order.OrderStatus;
import net.greeta.stock.common.domain.dto.order.events.OrderStatusChangedToAwaitingValidationDomainEvent;
import net.greeta.stock.ordering.shared.EventHandler;
import net.greeta.stock.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import java.util.stream.Collectors;

@EventHandler
@RequiredArgsConstructor
public class OrderStatusChangedToAwaitingValidationDomainEventHandler
    implements DomainEventHandler<OrderStatusChangedToAwaitingValidationDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationDomainEventHandler.class);

  private final OrderApplicationService orderApplicationService;
  private final IntegrationEventLogService integrationEventLogService;
  private final KafkaTopics topics;

  @EventListener
  public void handle(OrderStatusChangedToAwaitingValidationDomainEvent event) {
    logger.info(
        "Order with Id: {} has been successfully updated to status {}",
        event.orderId(),
        OrderStatus.AwaitingValidation
    );

    var order = orderApplicationService.findOrder(event.orderId());
    var buyer = orderApplicationService.findBuyerFor(order);
    var orderStockList = event.orderItems().stream()
        .map(orderItem -> new OrderStockItem(orderItem.getProductId(), orderItem.getUnits().getValue()))
        .collect(Collectors.toList());

    var orderStatusChangedToAwaitingValidationIntegrationEvent = new OrderStatusChangedToAwaitingValidationIntegrationEvent(
        order.getId().getUuid(), order.getOrderStatus().getStatus(), buyer.getBuyerName().getName(), orderStockList);
    integrationEventLogService.saveEvent(
        orderStatusChangedToAwaitingValidationIntegrationEvent,
        topics.getOrdersWaitingForValidation()
    );
  }
}
