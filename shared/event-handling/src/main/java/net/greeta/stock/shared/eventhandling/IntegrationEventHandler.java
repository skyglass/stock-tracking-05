package net.greeta.stock.shared.eventhandling;

public interface IntegrationEventHandler<T> {
  void handle(T event);
}
