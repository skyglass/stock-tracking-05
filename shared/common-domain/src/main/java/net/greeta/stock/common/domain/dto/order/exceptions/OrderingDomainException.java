package net.greeta.stock.common.domain.dto.order.exceptions;

public class OrderingDomainException extends RuntimeException {
  public OrderingDomainException(String message) {
    super(message);
  }
}
