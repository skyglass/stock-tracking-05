package net.greeta.stock.catalog.domain.catalogitem.rules;

import net.greeta.stock.catalog.domain.base.BusinessRule;
import net.greeta.stock.catalog.domain.catalogitem.Price;

public record PriceMustBeGreaterThanZero(
    Price price
) implements BusinessRule {

  @Override
  public boolean broken() {
    return price.isZero();
  }

  @Override
  public String message() {
    return "Product price must be greater than zero";
  }
}
