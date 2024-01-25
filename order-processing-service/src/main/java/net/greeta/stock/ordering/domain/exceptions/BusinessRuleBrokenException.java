package net.greeta.stock.ordering.domain.exceptions;

import net.greeta.stock.ordering.domain.base.BusinessRule;

public class BusinessRuleBrokenException extends RuntimeException {
  public BusinessRuleBrokenException(BusinessRule rule) {
    super(rule.message());
  }
}
