package net.greeta.stock.catalog.domain.catalogitem;

import org.axonframework.modelling.command.Aggregate;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public interface CatalogItemAggregateRepository {
  Optional<CatalogItemAggregate> load(UUID id);

  Aggregate<CatalogItemAggregate> loadAggregate(UUID id);

  Aggregate<CatalogItemAggregate> save(Supplier<CatalogItemAggregate> createCatalogItem);
}
