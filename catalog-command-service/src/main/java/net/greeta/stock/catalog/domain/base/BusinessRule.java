package net.greeta.stock.catalog.domain.base;

public interface BusinessRule {
  boolean broken();

  String message();
}
