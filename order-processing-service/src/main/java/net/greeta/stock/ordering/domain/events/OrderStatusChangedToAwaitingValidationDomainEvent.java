package net.greeta.stock.ordering.domain.events;

import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderId;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderItem;
import net.greeta.stock.ordering.domain.base.DomainEvent;

import java.util.List;

/**
 * Event used when the grace period order is confirmed.
 */
public record OrderStatusChangedToAwaitingValidationDomainEvent(
    OrderId orderId,
    List<OrderItem> orderItems) implements DomainEvent {
}
