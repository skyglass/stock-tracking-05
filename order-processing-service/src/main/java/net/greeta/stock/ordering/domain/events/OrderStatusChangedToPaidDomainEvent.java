package net.greeta.stock.ordering.domain.events;

import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderId;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderItem;
import net.greeta.stock.ordering.domain.base.DomainEvent;

import java.util.List;

/**
 * Event used when the order is paid.
 */
public record OrderStatusChangedToPaidDomainEvent(
    OrderId orderId,
    List<OrderItem> orderItems) implements DomainEvent {
}
