package net.greeta.stock.ordering.infrastructure.entity;

import net.greeta.stock.common.domain.dto.order.Order;
import net.greeta.stock.common.domain.dto.order.snapshot.OrderItemSnapshot;
import net.greeta.stock.common.domain.dto.order.snapshot.OrderSnapshot;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
class OrderEntityConverter implements EntityConverter<OrderEntity, Order> {

  @Override
  public OrderEntity toEntity(Order order) {
    var snapshot = order.snapshot();

    var orderEntity = OrderEntity.builder()
        .id(UUID.fromString(snapshot.getId()))
        .orderDate(snapshot.getOrderDate())
        .buyerId(snapshot.getBuyerId() != null ? UUID.fromString(snapshot.getBuyerId()) : null)
        .description(snapshot.getDescription())
        .isDraft(snapshot.isDraft())
        .orderStatus(order.getOrderStatus().getStatus())
        .requestId(order.getRequestId())
        .build();

    orderEntity.setOrderItems(snapshot.getOrderItems()
        .stream()
        .map(orderItemSnapshot -> toOrderItemEntity(orderItemSnapshot, orderEntity))
        .collect(Collectors.toSet()));

    return orderEntity;
  }

  private OrderItemEntity toOrderItemEntity(OrderItemSnapshot snapshot, OrderEntity orderEntity) {
    return OrderItemEntity.builder()
        .id(UUID.fromString(snapshot.getId()))
        .discount(snapshot.getDiscount())
        .pictureUrl(snapshot.getPictureUrl())
        .productId(snapshot.getProductId())
        .productName(snapshot.getProductName())
        .unitPrice(snapshot.getUnitPrice())
        .units(snapshot.getUnits())
        .order(orderEntity)
        .build();
  }

  @Override
  public Order fromEntity(OrderEntity orderEntity) {
    return Order.rehydrate(OrderSnapshot.builder()
        .id(orderEntity.getId().toString())
        .orderStatus(orderEntity.getOrderStatus())
        .orderDate(orderEntity.getOrderDate())
        .draft(orderEntity.isDraft())
        .buyerId(orderEntity.getBuyerId() != null ? orderEntity.getBuyerId().toString() : null)
        .orderItems(orderEntity.getOrderItems().stream()
            .map(this::toOrderItemSnapshot)
            .collect(Collectors.toList()))
        .requestId(orderEntity.getRequestId())
        .build());
  }

  private OrderItemSnapshot toOrderItemSnapshot(OrderItemEntity orderItemEntity) {
    return OrderItemSnapshot.builder()
        .id(orderItemEntity.getId().toString())
        .units(orderItemEntity.getUnits())
        .productId(orderItemEntity.getProductId())
        .unitPrice(orderItemEntity.getUnitPrice())
        .productName(orderItemEntity.getProductName())
        .pictureUrl(orderItemEntity.getPictureUrl())
        .discount(orderItemEntity.getDiscount())
        .build();
  }

}
