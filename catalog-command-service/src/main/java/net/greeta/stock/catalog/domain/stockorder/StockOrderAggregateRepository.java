package net.greeta.stock.catalog.domain.stockorder;

import org.axonframework.modelling.command.Aggregate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public interface StockOrderAggregateRepository {
  Optional<StockOrderAggregate> load(UUID id);

  Aggregate<StockOrderAggregate> loadAggregate(UUID id);

  Aggregate<StockOrderAggregate> save(Supplier<StockOrderAggregate> stockOrderSupplier);
}
