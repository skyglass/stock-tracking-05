package net.greeta.stock.ordering.domain.events;

import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderId;
import net.greeta.stock.ordering.domain.base.DomainEvent;

public record OrderStatusChangedToStockConfirmedDomainEvent(OrderId orderId) implements DomainEvent {
}
