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
@NoArgsConstructor
public class BasketItem implements Serializable {
  @Setter
  private String id;
  @NotNull(message = "Product id is required")
  private UUID productId;
  @NotEmpty(message = "Product name is required")
  private String productName;
  @Setter
  private Double unitPrice;
  @Setter
  private Double oldUnitPrice;
  @Min(value = 1, message = "Invalid number of units")
  private Integer quantity;
  private String pictureUrl;
}
