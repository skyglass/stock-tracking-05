package net.greeta.stock.ordering.domain.base;

import org.springframework.lang.NonNull;

public interface Repository<T extends AggregateRoot> {
  T save(@NonNull T aggregateRoot);
}
