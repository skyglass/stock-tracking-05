package net.greeta.stock.shared.eventhandling.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class StockRemoved extends Event {
  @JsonProperty
  @NotNull
  private UUID orderId;
  @JsonProperty
  @NotNull @Min(1)
  private Integer quantity;
  @JsonProperty
  @NotNull @Min(0)
  private Integer availableStock;

  public StockRemoved(UUID productId, UUID orderId, Integer quantity, Integer availableStock) {
    super(productId);
    this.orderId = orderId;
    this.quantity = quantity;
    this.availableStock = availableStock;
  }
}
