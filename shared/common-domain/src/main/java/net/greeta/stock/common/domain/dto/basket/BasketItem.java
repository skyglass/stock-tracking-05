package net.greeta.stock.common.domain.dto.basket;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BasketItem implements Serializable {
  private String id;
  @NotNull(message = "Product id is required")
  private UUID productId;
  @NotEmpty(message = "Product name is required")
  private String productName;
  private Double unitPrice;
  private Double oldUnitPrice;
  @Min(value = 1, message = "Invalid number of units")
  private Integer quantity;
  private String pictureUrl;
}
