package net.greeta.stock.common.domain.dto.order.events;

import net.greeta.stock.common.domain.dto.order.OrderId;
import net.greeta.stock.common.domain.dto.order.base.DomainEvent;

public record OrderStatusChangedToStockConfirmedDomainEvent(OrderId orderId) implements DomainEvent {
}
