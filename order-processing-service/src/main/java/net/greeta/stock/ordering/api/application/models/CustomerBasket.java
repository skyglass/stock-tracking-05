package net.greeta.stock.ordering.api.application.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.common.domain.dto.order.BasketItem;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CustomerBasket {
  private String buyerId;
  private final List<BasketItem> items = new ArrayList<>();

  public CustomerBasket(String customerId) {
    buyerId = customerId;
  }
}
