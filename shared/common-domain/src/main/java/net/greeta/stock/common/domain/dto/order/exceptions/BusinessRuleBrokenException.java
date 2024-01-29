package net.greeta.stock.common.domain.dto.order.exceptions;

import net.greeta.stock.common.domain.dto.order.base.BusinessRule;

public class BusinessRuleBrokenException extends RuntimeException {
  public BusinessRuleBrokenException(BusinessRule rule) {
    super(rule.message());
  }
}
