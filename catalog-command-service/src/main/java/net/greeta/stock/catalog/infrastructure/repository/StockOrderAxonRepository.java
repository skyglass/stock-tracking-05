package net.greeta.stock.catalog.infrastructure.repository;

import net.greeta.stock.catalog.domain.stockorder.StockOrderAggregate;
import net.greeta.stock.catalog.domain.stockorder.StockOrderAggregateRepository;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.spring.config.SpringAxonConfiguration;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@Repository
public class StockOrderAxonRepository implements StockOrderAggregateRepository {

    private final org.axonframework.modelling.command.Repository<StockOrderAggregate> repository;

    StockOrderAxonRepository(SpringAxonConfiguration axonConfiguration) {
        repository = axonConfiguration.getObject().repository(StockOrderAggregate.class);
    }

    @Override
    public Optional<StockOrderAggregate> load(UUID id) {
        try {
            return Optional.of(repository.load(id.toString()).invoke(Function.identity()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Aggregate<StockOrderAggregate> loadAggregate(UUID id) {
        return repository.load(id.toString());
    }

    @Override
    public Aggregate<StockOrderAggregate> save(Supplier<StockOrderAggregate> stockOrderSupplier) {
        try {
            return repository.newInstance(stockOrderSupplier::get);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create catalog order");
        }
    }
}
