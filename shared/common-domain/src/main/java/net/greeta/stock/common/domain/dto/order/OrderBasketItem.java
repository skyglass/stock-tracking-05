package net.greeta.stock.common.domain.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OrderBasketItem {
  @JsonProperty
  private String id;

  @JsonProperty
  @NotNull
  private UUID productId;

  @JsonProperty
  @NotNull
  private String productName;

  @JsonProperty
  @NotNull
  private Double unitPrice;

  @JsonProperty
  private Double oldUnitPrice;

  @JsonProperty
  @NotNull
  @Min(value = 1, message = "Invalid quantity")
  private Integer quantity;

  @JsonProperty
  private String pictureUrl;

  public static OrderItemDTO toOrderItemDTO(OrderBasketItem item) {
    return new OrderItemDTO(
      item.getProductId(),
      item.getProductName(),
      item.getUnitPrice(),
      0D,
      item.getQuantity(),
      item.getPictureUrl()
    );
  }
}