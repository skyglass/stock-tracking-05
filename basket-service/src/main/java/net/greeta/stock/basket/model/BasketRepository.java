package net.greeta.stock.basket.model;

import net.greeta.stock.common.domain.dto.basket.CustomerBasket;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BasketRepository {

  Optional<CustomerBasket> findById(UUID basketId);

  Optional<CustomerBasket> findByCustomerId(String customerId);

  Set<String> getUsers();

  CustomerBasket updateBasket(CustomerBasket basket);

  void delete(UUID basketId);
}
