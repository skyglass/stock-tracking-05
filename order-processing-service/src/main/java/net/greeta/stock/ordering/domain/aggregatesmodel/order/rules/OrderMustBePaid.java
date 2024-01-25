package net.greeta.stock.ordering.domain.aggregatesmodel.order.rules;

import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderStatus;
import net.greeta.stock.ordering.domain.base.BusinessRule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderMustBePaid implements BusinessRule {
  private final OrderStatus currentStatus;

  @Override
  public boolean broken() {
    return !OrderStatus.Paid.equals(currentStatus);
  }

  @Override
  public String message() {
    return "It's not possible to ship order that's not paid. The status of the order is %s.".formatted(currentStatus);
  }
}
