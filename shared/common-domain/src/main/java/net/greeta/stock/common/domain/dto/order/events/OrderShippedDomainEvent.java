package net.greeta.stock.common.domain.dto.order.events;

import net.greeta.stock.common.domain.dto.order.Order;
import net.greeta.stock.common.domain.dto.order.base.DomainEvent;

public record OrderShippedDomainEvent(
    Order order) implements DomainEvent {
}
