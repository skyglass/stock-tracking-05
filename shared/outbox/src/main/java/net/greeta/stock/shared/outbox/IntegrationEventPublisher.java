package net.greeta.stock.shared.outbox;

public interface IntegrationEventPublisher {
  void publish(IntegrationEventLogEntry eventLogEntry);
}
