package net.greeta.stock.catalog.domain.catalogitem.rules;

import net.greeta.stock.catalog.domain.base.BusinessRule;
import net.greeta.stock.catalog.domain.catalogitem.ProductName;
import net.greeta.stock.catalog.domain.catalogitem.Units;

public record AvailableStockMustNotBeEmpty(
    ProductName name,
    Units availableStock
) implements BusinessRule {

  @Override
  public boolean broken() {
    return availableStock.isEmpty();
  }

  @Override
  public String message() {
    return "Empty stock, product item %s is sold out".formatted(name.getName());
  }
}
