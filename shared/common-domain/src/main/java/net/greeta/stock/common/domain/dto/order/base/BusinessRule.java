package net.greeta.stock.common.domain.dto.order.base;

public interface BusinessRule {
  boolean broken();

  String message();
}
