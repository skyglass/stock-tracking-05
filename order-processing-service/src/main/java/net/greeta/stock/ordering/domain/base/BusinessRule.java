package net.greeta.stock.ordering.domain.base;

public interface BusinessRule {
  boolean broken();

  String message();
}
