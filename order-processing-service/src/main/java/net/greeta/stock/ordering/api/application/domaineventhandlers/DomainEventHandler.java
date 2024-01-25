package net.greeta.stock.ordering.api.application.domaineventhandlers;

public interface DomainEventHandler<E> {
  void handle(E event);
}
