package net.greeta.stock.ordering.domain.events;

import net.greeta.stock.ordering.domain.aggregatesmodel.order.Order;
import net.greeta.stock.ordering.domain.base.DomainEvent;

public record OrderCancelledDomainEvent(Order order) implements DomainEvent {
}
