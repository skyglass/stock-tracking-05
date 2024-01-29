package net.greeta.stock.common.domain.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderItem {
  private String id;
  private String name;
  private Double unitPrice;
  private Integer units;
}
