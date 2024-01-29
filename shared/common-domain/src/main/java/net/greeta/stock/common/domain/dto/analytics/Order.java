package net.greeta.stock.common.domain.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order {
  private String id;
  private Double totalPrice;
  private List<OrderItem> orderItems;
}
