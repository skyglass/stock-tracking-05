package net.greeta.stock.ordering.domain.aggregatesmodel.order.rules;

import net.greeta.stock.ordering.domain.aggregatesmodel.order.Price;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.Units;
import net.greeta.stock.ordering.domain.base.BusinessRule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnitsPriceMustBeGreaterThanDiscount implements BusinessRule {
  private final Price unitPrice;
  private final Units units;
  private final Price discount;

  @Override
  public boolean broken() {
    return unitPrice.multiply(units).lessThan(discount);
  }

  @Override
  public String message() {
    return "The total of order item is lower than applied discount";
  }
}
