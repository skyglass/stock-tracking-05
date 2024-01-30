package net.greeta.stock.common.domain.dto.basket;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasketCheckout {
  @NotEmpty
  private String buyer;
  @Setter
  private UUID requestId;
}
