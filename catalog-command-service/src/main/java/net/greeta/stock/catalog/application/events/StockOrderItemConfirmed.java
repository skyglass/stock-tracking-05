package net.greeta.stock.catalog.application.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.catalog.application.integrationevents.events.StockOrderItem;
import net.greeta.stock.shared.eventhandling.events.Event;

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
