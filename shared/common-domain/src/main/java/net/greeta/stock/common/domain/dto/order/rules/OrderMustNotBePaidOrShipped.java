package net.greeta.stock.common.domain.dto.order.rules;

import net.greeta.stock.common.domain.dto.order.OrderStatus;
import net.greeta.stock.common.domain.dto.order.base.BusinessRule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderMustNotBePaidOrShipped implements BusinessRule {
  private final OrderStatus currentStatus;

  @Override
  public boolean broken() {
    return OrderStatus.Paid.equals(currentStatus) || OrderStatus.Shipped.equals(currentStatus);
  }

  @Override
  public String message() {
    return "It's not possible to cancel order with status {%s}.".formatted(currentStatus);
  }
}
