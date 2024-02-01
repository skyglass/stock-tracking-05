package net.greeta.stock.ordering.api.application.queries;

import net.greeta.stock.common.domain.dto.order.OrderViewModel;
import net.greeta.stock.ordering.shared.QueryHandler;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@QueryHandler
@RequiredArgsConstructor
public class OrderQueriesImpl implements OrderQueries {
  private final EntityManager entityManager;

  @Override
  public Optional<OrderViewModel.Order> getOrder(String id) {
    var query = entityManager.createNativeQuery("""
        SELECT cast(o.id as varchar) as orderNumber, o.order_date as date, o.description,
          o.order_status as status,
          oi.product_name as productName, oi.units, oi.unit_price as unitPrice, oi.picture_url as pictureUrl,
          cast(oi.id as varchar), cast(oi.product_id as varchar), ob.user_id, cast(o.request_id as varchar)
        FROM orders o
        LEFT JOIN order_item oi on o.id = oi.order_id
        LEFT JOIN buyer ob on o.buyer_id = ob.id
        WHERE o.id = ?1
      """).setParameter(1, UUID.fromString(id));

    return isNotEmpty(query.getResultList())
      ? Optional.of(toOrder(query.getResultList()))
      : Optional.empty();
  }

  @Override
  public Optional<OrderViewModel.Order> getOrderByRequestId(String requestId) {
    var query = entityManager.createNativeQuery("""
        SELECT cast(o.id as varchar) as orderNumber, o.order_date as date, o.description,
          o.order_status as status,
          oi.product_name as productName, oi.units, oi.unit_price as unitPrice, oi.picture_url as pictureUrl,
          cast(oi.id as varchar), cast(oi.product_id as varchar), ob.user_id, cast(o.request_id as varchar)
        FROM orders o
        LEFT JOIN order_item oi on o.id = oi.order_id
        LEFT JOIN buyer ob on o.buyer_id = ob.id
        WHERE o.request_id = ?1
      """).setParameter(1, UUID.fromString(requestId));

    return isNotEmpty(query.getResultList())
            ? Optional.of(toOrder(query.getResultList()))
            : Optional.empty();
  }

  @Override
  public List<OrderViewModel.Order> userOrders(String userId) {
    var query = entityManager.createNativeQuery("""
      SELECT cast(o.id as varchar) as orderNumber, o.order_date as date, o.description,
          o.order_status as status,
          oi.product_name as productName, oi.units, oi.unit_price as unitPrice, oi.picture_url as pictureUrl,
          cast(oi.id as varchar), cast(oi.product_id as varchar), ob.user_id, cast(o.request_id as varchar)
      FROM orders o
      LEFT JOIN order_item oi ON o.id = oi.order_id
      LEFT JOIN buyer ob on o.buyer_id = ob.id
      WHERE ob.user_id = :userId
      ORDER BY o.order_date
      """).setParameter("userId", userId);

    List<Object[]> results = query.getResultList();
    var byOrderId = results.stream()
      .collect(Collectors.groupingBy(o -> o[0]));

    return byOrderId.values().stream()
      .map(this::toOrder)
      .toList();
  }

  @Override
  public List<OrderViewModel.Order> allOrders() {
    var query = entityManager.createNativeQuery("""
      SELECT cast(o.id as varchar) as orderNumber, o.order_date as date, o.description,
          o.order_status as status,
          oi.product_name as productName, oi.units, oi.unit_price as unitPrice, oi.picture_url as pictureUrl,
          cast(oi.id as varchar), cast(oi.product_id as varchar), ob.user_id, cast(o.request_id as varchar)
      FROM orders o
      LEFT JOIN order_item oi ON o.id = oi.order_id
      LEFT JOIN buyer ob on o.buyer_id = ob.id
      ORDER BY o.order_date
      """);

    List<Object[]> results = query.getResultList();
    var byOrderId = results.stream()
      .collect(Collectors.groupingBy(o -> o[0]));

    return byOrderId.values().stream()
      .map(this::toOrder)
      .toList();
  }

  @Override
  public List<OrderViewModel.OrderSummary> getOrdersFromUser(String userId) {
    var query = entityManager.createNativeQuery("""
      SELECT cast(o.id as varchar) as orderNumber, o.order_date as date, o.order_status as status, sum(oi.units * oi.unit_price) as total
      FROM orders o
      LEFT JOIN order_item oi ON o.id = oi.order_id
      LEFT JOIN buyer ob on o.buyer_id = ob.id
      WHERE ob.user_id = :userId
      GROUP BY o.id, o.order_date, o.order_status
      ORDER BY o.id
      """).setParameter("userId", userId);

    return toOrderSummaries(query.getResultList());
  }

  private List<OrderViewModel.OrderSummary> toOrderSummaries(List<Object[]> result) {
    return result.stream()
      .map(r -> {
        var id = (String) r[0];
        var date = (Timestamp) r[1];
        var status = (String) r[2];
        var total = (Double) r[3];

        return new OrderViewModel.OrderSummary(
          id,
          date.toLocalDateTime(),
          status,
          total
        );
      }).collect(Collectors.toList());
  }

  private OrderViewModel.Order toOrder(List<Object[]> result) {
    var orderItems = result.stream().map(r -> {
      var productName = (String) r[4];
      var units = (Integer) r[5];
      var unitPrice = (Double) r[6];
      var pictureUrl = (String) r[7];
      var orderItemId = (String) r[8];
      var productId = (String) r[9];

      return new OrderViewModel.OrderItem(
        orderItemId,
        productId,
        productName,
        units,
        unitPrice,
        pictureUrl
      );
    }).collect(Collectors.toList());

    var orderDetails = result.get(0);

    var orderId = (String) orderDetails[0];
    var date = (Timestamp) orderDetails[1];
    var description = (String) orderDetails[2];
    var status = (String) orderDetails[3];
    var ownerId = (String) orderDetails[10];
    var requestId = (String) orderDetails[11];

    var total = orderItems.stream()
      .map(item -> item.units() * item.unitPrice())
      .reduce(Double::sum)
      .orElse(0D);

    return new OrderViewModel.Order(
      UUID.fromString(orderId),
      orderId,
      date.toLocalDateTime(),
      status,
      description,
      orderItems,
      total,
      ownerId,
      requestId
    );
  }
}
