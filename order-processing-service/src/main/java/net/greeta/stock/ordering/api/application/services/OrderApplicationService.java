package net.greeta.stock.ordering.api.application.services;

import net.greeta.stock.ordering.domain.aggregatesmodel.buyer.Buyer;
import net.greeta.stock.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.Order;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderId;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderRepository;
import net.greeta.stock.ordering.shared.ApplicationService;
import net.greeta.stock.shared.rest.error.NotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class OrderApplicationService {
  private final OrderRepository orderRepository;
  private final BuyerRepository buyerRepository;

  public Order findOrder(OrderId orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order %s not found".formatted(orderId.toString())));
  }

  public Buyer findBuyerFor(Order order) {
    return order.buyerId()
        .flatMap(buyerRepository::findById)
        .orElseThrow(() -> new NotFoundException("Buyer not found for order %s".formatted(order.getId().getUuid())));
  }
}
