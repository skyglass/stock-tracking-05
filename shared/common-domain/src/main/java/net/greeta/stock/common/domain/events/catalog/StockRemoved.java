package net.greeta.stock.common.domain.events.catalog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.common.domain.dto.catalog.ConfirmedStockOrderItem;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class StockRemoved extends Event {
  private Integer availableStock;
  private ConfirmedStockOrderItem confirmedOrderStockItem;

  public StockRemoved(UUID id, Integer availableStock) {
    super(id);
    this.availableStock = availableStock;
  }
}
