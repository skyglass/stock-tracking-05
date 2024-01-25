package net.greeta.stock.shared.outbox;

import net.greeta.stock.shared.eventhandling.IntegrationEvent;

import java.util.List;

public interface IntegrationEventLogService {
  List<IntegrationEventLogEntry> retrieveEventLogsPendingToPublish();

  void markEventAsInProgress(IntegrationEventLogEntry eventLogEntry);

  void markEventAsPublished(IntegrationEventLogEntry eventLogEntry);

  void markEventAsFailed(IntegrationEventLogEntry eventLogEntry);

  void saveEvent(IntegrationEvent event, String topic);
}
