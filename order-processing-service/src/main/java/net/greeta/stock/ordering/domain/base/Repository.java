package net.greeta.stock.ordering.domain.base;

import net.greeta.stock.common.domain.dto.order.base.AggregateRoot;
import org.springframework.lang.NonNull;

public interface Repository<T extends AggregateRoot> {
  T save(@NonNull T aggregateRoot);
}
