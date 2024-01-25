package net.greeta.stock.shared.outbox;

public enum EventState {
  NotPublished,
  InProgress,
  Published,
  PublishedFailed
}
