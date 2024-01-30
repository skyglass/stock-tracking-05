package net.greeta.stock.common.domain.dto.basket;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class CustomerBasket implements Serializable {
  private UUID id;

  @NotEmpty(message = "Buyer id is required")
  private String buyerId;

  private BasketStatus status = BasketStatus.New;

  private List<BasketItem> items = new ArrayList<>();

    public CustomerBasket(String buyerId) {
        this(UUID.randomUUID(), buyerId, new ArrayList<>());
    }

  public CustomerBasket(UUID id, String buyerId, List<BasketItem> items) {
      this.id = id;
      this.buyerId = buyerId;
      this.items = items;
  }

  public void changeStatusTo(BasketStatus status) {
    this.status = status;
  }
}
