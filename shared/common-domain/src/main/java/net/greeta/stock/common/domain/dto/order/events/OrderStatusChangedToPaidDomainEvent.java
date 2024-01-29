package net.greeta.stock.common.domain.dto.order.events;

import net.greeta.stock.common.domain.dto.order.OrderId;
import net.greeta.stock.common.domain.dto.order.OrderItem;
import net.greeta.stock.common.domain.dto.order.base.DomainEvent;

import java.util.List;

/**
 * Event used when the order is paid.
 */
public record OrderStatusChangedToPaidDomainEvent(
    OrderId orderId,
    List<OrderItem> orderItems) implements DomainEvent {
}
