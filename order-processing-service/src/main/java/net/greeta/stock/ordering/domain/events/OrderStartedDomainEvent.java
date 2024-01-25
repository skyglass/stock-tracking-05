package net.greeta.stock.ordering.domain.events;

import net.greeta.stock.ordering.domain.aggregatesmodel.buyer.*;
import net.greeta.stock.ordering.domain.aggregatesmodel.buyer.*;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.Order;
import net.greeta.stock.ordering.domain.base.DomainEvent;

/**
 * Event used when an order is created.
 */
public record OrderStartedDomainEvent(
    Order order,
    UserId userId,
    BuyerName buyerName,
    CardType cardType,
    CardNumber cardNumber,
    SecurityNumber cardSecurityNumber,
    CardHolder cardHolderName,
    CardExpiration cardExpiration
) implements DomainEvent {
}
