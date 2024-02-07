package net.greeta.stock.catalog.application.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.catalog.application.integrationevents.events.StockOrderItem;
import net.greeta.stock.shared.eventhandling.events.Event;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class StockOrderCreated extends Event {

    private List<StockOrderItem> stockOrderItems;

    public StockOrderCreated(UUID orderId, List<StockOrderItem> stockOrderItems) {
        super(orderId);
        this.stockOrderItems = stockOrderItems;
    }
}
