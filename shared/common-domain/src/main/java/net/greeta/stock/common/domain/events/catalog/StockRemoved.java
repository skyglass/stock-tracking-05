package net.greeta.stock.common.domain.events.catalog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.common.domain.dto.catalog.ConfirmedOrderStockItem;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class StockRemoved extends Event {
  private Integer availableStock;
  private ConfirmedOrderStockItem confirmedOrderStockItem;

  public StockRemoved(UUID id, Integer availableStock, ConfirmedOrderStockItem confirmedOrderStockItem) {
    super(id);
    this.availableStock = availableStock;
    this.confirmedOrderStockItem = confirmedOrderStockItem;
  }
}
