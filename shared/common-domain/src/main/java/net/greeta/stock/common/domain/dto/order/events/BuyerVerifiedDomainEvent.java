package net.greeta.stock.common.domain.dto.order.events;

import net.greeta.stock.common.domain.dto.order.OrderId;
import net.greeta.stock.common.domain.dto.order.base.DomainEvent;
import net.greeta.stock.common.domain.dto.order.buyer.Buyer;

public record BuyerVerifiedDomainEvent(
    Buyer buyer,
    OrderId orderId) implements DomainEvent {
}
