package net.greeta.stock.catalog.domain.stockorder.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.common.domain.dto.catalog.ConfirmedStockOrderItem;
import net.greeta.stock.common.domain.dto.catalog.StockOrderItem;
import net.greeta.stock.common.domain.events.catalog.Event;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class StockOrderItemConfirmed extends Event {
    private UUID productId;
    private Integer quantity;
    private StockOrderItem next;
    private boolean hasStock;

    public StockOrderItemConfirmed(UUID orderId, UUID productId, Integer quantity,
                                   StockOrderItem next, boolean hasStock) {
        super(orderId);
        this.productId = productId;
        this.quantity = quantity;
        this.next = next;
        this.hasStock = hasStock;
    }
}
