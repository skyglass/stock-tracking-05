package net.greeta.stock.common.domain.dto.order.events;

import net.greeta.stock.common.domain.dto.order.buyer.*;
import net.greeta.stock.common.domain.dto.order.Order;
import net.greeta.stock.common.domain.dto.order.base.DomainEvent;

/**
 * Event used when an order is created.
 */
public record OrderStartedDomainEvent(
    Order order,
    UserId userId,
    BuyerName buyerName
) implements DomainEvent {
}
