package net.greeta.stock.basket.services;

import lombok.AllArgsConstructor;
import net.greeta.stock.basket.controller.BasketController;
import net.greeta.stock.basket.integrationevents.events.UserCheckoutAcceptedIntegrationEvent;
import net.greeta.stock.basket.model.BasketRepository;
import net.greeta.stock.common.domain.dto.basket.BasketCheckout;
import net.greeta.stock.common.domain.dto.basket.BasketStatus;
import net.greeta.stock.common.domain.dto.basket.CustomerBasket;
import net.greeta.stock.shared.eventhandling.EventBus;
import net.greeta.stock.shared.rest.error.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.util.CollectionUtils.isEmpty;

@AllArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {
  private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

  private final BasketRepository basketRepository;
  private final IdentityService identityService;
  private final EventBus orderCheckoutsEventBus;

  @Override
  public Optional<CustomerBasket> findById(UUID basketId) {
    return basketRepository.findById(basketId);
  }

  @Override
  public CustomerBasket findByCustomerId(String customerId) {
    return getCustomerBasket(customerId);
  }

  @Override
  public CustomerBasket updateBasket(CustomerBasket updatedBasket) {
    var basketToUpdate = basketRepository.findByCustomerId(updatedBasket.getBuyerId())
      .map(b -> new CustomerBasket(b.getId(), updatedBasket.getBuyerId(), updatedBasket.getItems()))
      .orElseGet(() -> new CustomerBasket(UUID.randomUUID(), updatedBasket.getBuyerId(), updatedBasket.getItems()));

    return basketRepository.updateBasket(basketToUpdate);
  }

  @Transactional
  @Override
  public void checkout(BasketCheckout basketCheckout) {
    var userName = identityService.getUserName();
    var basket = getCustomerBasket(userName);

    var event = new UserCheckoutAcceptedIntegrationEvent(
      userName,
      userName,
      basketCheckout.getBuyer(),
      basketCheckout.getRequestId(),
      basket
    );

    basketRepository.updateBasket(basket);

    // Once basket is checkout, sends an integration event to order-processor to convert basket to order and proceeds
    // with order creation process.
    orderCheckoutsEventBus.publish(event);
  }

  @Transactional
  @Override
  public void directCheckout(CustomerBasket basket, UUID requestId) {
    var userName = identityService.getUserName();

    var event = new UserCheckoutAcceptedIntegrationEvent(
            userName,
            userName,
            basket.getBuyerId(),
            requestId,
            basket
    );

    // Once basket is checkout, sends an integration event to order-processor to convert basket to order and proceeds
    // with order creation process.
    orderCheckoutsEventBus.publish(event);
  }

  @Override
  public void delete(UUID basketId) {
    basketRepository.delete(basketId);
  }

  private CustomerBasket getCustomerBasket(String customerId) {
    return basketRepository.findByCustomerId(customerId)
      .orElseGet(() -> basketRepository.updateBasket(
              new CustomerBasket(customerId)));
  }
}
