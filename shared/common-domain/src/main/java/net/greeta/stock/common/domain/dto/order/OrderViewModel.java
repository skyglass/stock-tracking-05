package net.greeta.stock.common.domain.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class OrderViewModel {
  private OrderViewModel() {
  }

  public record OrderItem(
    String id,
    String productId,
    String productName,
    Integer units,
    Double unitPrice,
    String pictureUrl
  ) {
  }

  public record Order(
    UUID id,
    String orderNumber,
    LocalDateTime date,
    String status,
    String description,
    List<OrderItem> orderItems,
    Double total,
    String ownerId
  ) {
  }

  public record OrderSummary(
    String orderNumber,
    LocalDateTime date,
    String status,
    Double total
  ) {
  }

}
