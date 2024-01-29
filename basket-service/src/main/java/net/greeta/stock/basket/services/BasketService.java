package net.greeta.stock.basket.services;

import net.greeta.stock.common.domain.dto.basket.BasketCheckout;
import net.greeta.stock.common.domain.dto.basket.CustomerBasket;

import java.util.Optional;
import java.util.UUID;


public interface BasketService {
  Optional<CustomerBasket> findById(UUID basketId);

  CustomerBasket findByCustomerId(String customerId);

  CustomerBasket updateBasket(CustomerBasket basket);

  void checkout(BasketCheckout basketCheckout);

  void delete(UUID basketId);
}
