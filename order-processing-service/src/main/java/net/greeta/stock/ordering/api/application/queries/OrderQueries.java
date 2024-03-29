package net.greeta.stock.ordering.api.application.queries;

import net.greeta.stock.common.domain.dto.order.OrderViewModel;

import java.util.List;
import java.util.Optional;

public interface OrderQueries {
  Optional<OrderViewModel.Order> getOrder(String id);

  Optional<OrderViewModel.Order> getOrderByRequestId(String requestId);

  List<OrderViewModel.Order> userOrders(String userId);

  List<OrderViewModel.OrderSummary> getOrdersFromUser(String userId);

  List<OrderViewModel.Order> allOrders();
}
