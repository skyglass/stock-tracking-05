package net.greeta.stock.common.domain.dto.basket;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class BasketCheckout {
  @NotEmpty
  private String buyer;
  @Setter
  private UUID requestId;
}
