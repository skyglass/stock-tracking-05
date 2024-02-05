package net.greeta.stock.catalog.infrastructure.repository;

import net.greeta.stock.catalog.domain.catalogitem.CatalogItemAggregate;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemAggregateRepository;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.spring.config.SpringAxonConfiguration;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@Repository
public class CatalogItemAxonRepository implements CatalogItemAggregateRepository {

  private final org.axonframework.modelling.command.Repository<CatalogItemAggregate> repository;

  CatalogItemAxonRepository(SpringAxonConfiguration axonConfiguration) {
    repository = axonConfiguration.getObject().repository(CatalogItemAggregate.class);
  }

  @Override
  public Optional<CatalogItemAggregate> load(UUID id) {
    try {
      return Optional.of(repository.load(id.toString()).invoke(Function.identity()));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public Aggregate<CatalogItemAggregate> loadAggregate(UUID id) {
    return repository.load(id.toString());
  }

  @Override
  public Aggregate<CatalogItemAggregate> save(Supplier<CatalogItemAggregate> createCatalogItem) {
    try {
      return repository.newInstance(createCatalogItem::get);
    } catch (Exception e) {
      throw new RuntimeException("Cannot create catalog item");
    }
  }
}
