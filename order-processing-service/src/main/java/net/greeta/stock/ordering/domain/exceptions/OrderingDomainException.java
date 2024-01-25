package net.greeta.stock.ordering.domain.exceptions;

public class OrderingDomainException extends RuntimeException {
  public OrderingDomainException(String message) {
    super(message);
  }
}
