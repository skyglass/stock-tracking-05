package net.greeta.stock.catalog.application.integrationevents;

import net.greeta.stock.shared.eventhandling.IntegrationEvent;

public interface IntegrationEventPublisher {
  void publish(String topic, IntegrationEvent event);
}
