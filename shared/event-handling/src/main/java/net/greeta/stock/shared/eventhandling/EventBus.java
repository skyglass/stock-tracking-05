package net.greeta.stock.shared.eventhandling;

public interface EventBus {
  void publish(IntegrationEvent event);
}
