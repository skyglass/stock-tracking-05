package net.greeta.stock.catalog.application.integrationevents.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConfirmedStockOrderItem {
  private UUID productId;
  private Integer quantity;
  private Boolean hasStock;
}
