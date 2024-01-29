package net.greeta.stock.ordering.domain.aggregatesmodel.buyer;

import net.greeta.stock.common.domain.dto.order.buyer.Buyer;
import net.greeta.stock.common.domain.dto.order.buyer.BuyerId;
import net.greeta.stock.common.domain.dto.order.buyer.UserId;
import net.greeta.stock.ordering.domain.base.Repository;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * This is just the RepositoryContracts or Interface defined at the Domain Layer
 * as requisite for the Buyer Aggregate.
 */
public interface BuyerRepository extends Repository<Buyer> {
  Optional<Buyer> findByUserId(@NonNull UserId userId);

  Optional<Buyer> findById(@NonNull BuyerId id);
}
