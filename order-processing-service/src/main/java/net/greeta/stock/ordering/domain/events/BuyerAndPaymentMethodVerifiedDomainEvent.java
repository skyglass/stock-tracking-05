package net.greeta.stock.ordering.domain.events;

import net.greeta.stock.ordering.domain.aggregatesmodel.buyer.Buyer;
import net.greeta.stock.ordering.domain.aggregatesmodel.buyer.PaymentMethod;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderId;
import net.greeta.stock.ordering.domain.base.DomainEvent;

public record BuyerAndPaymentMethodVerifiedDomainEvent(
    Buyer buyer,
    PaymentMethod payment,
    OrderId orderId) implements DomainEvent {
}
