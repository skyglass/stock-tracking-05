package net.greeta.stock.ordering.api.application.domaineventhandlers.orderstartedevent;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.common.domain.dto.order.buyer.Buyer;
import net.greeta.stock.common.domain.dto.order.events.OrderStartedDomainEvent;
import net.greeta.stock.ordering.api.application.domaineventhandlers.DomainEventHandler;
import net.greeta.stock.ordering.api.application.integrationevents.events.OrderStatusChangedToSubmittedIntegrationEvent;
import net.greeta.stock.ordering.config.KafkaTopics;
import net.greeta.stock.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import net.greeta.stock.ordering.shared.EventHandler;
import net.greeta.stock.shared.outbox.IntegrationEventLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import java.util.stream.Collectors;

@EventHandler
@RequiredArgsConstructor
public class ValidateOrAddBuyerAggregateWhenOrderStartedDomainEventHandler implements DomainEventHandler<OrderStartedDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(ValidateOrAddBuyerAggregateWhenOrderStartedDomainEventHandler.class);

  private final IntegrationEventLogService integrationEventLogService;
  private final BuyerRepository buyerRepository;
  private final KafkaTopics topics;

  @EventListener
  public void handle(OrderStartedDomainEvent orderStartedEvent) {
    final var buyer = verify(orderStartedEvent);
    final var order = orderStartedEvent.order();
    final var orderItems = order.getOrderItems().stream()
        .map(orderItem -> new OrderStatusChangedToSubmittedIntegrationEvent.OrderItemDto(
            order.getId().getUuid(),
            orderItem.orderItemProductName(),
            orderItem.getUnitPrice().getValue(),
            orderItem.getUnits().getValue()
        )).collect(Collectors.toList());
    final var orderStatusChangedToSubmittedIntegrationEvent = new OrderStatusChangedToSubmittedIntegrationEvent(
        order.getId().getUuid(),
        order.getOrderStatus().getStatus(),
        buyer.getBuyerName().getName(),
        order.getTotal().getValue(),
        orderItems
    );

    integrationEventLogService.saveEvent(orderStatusChangedToSubmittedIntegrationEvent, topics.getSubmittedOrders());

    logger.info(
        "Buyer {} was validated or updated for orderId: {}.",
        buyer.getBuyerName(),
        order.getId()
    );
  }

  private Buyer verify(OrderStartedDomainEvent orderStartedEvent) {
    final var buyer = buyerRepository.findByUserId(orderStartedEvent.userId())
        .orElseGet(() -> new Buyer(orderStartedEvent.userId(), orderStartedEvent.buyerName()));
    buyer.verify(orderStartedEvent.order().getId());
    return buyerRepository.save(buyer);
  }

}
