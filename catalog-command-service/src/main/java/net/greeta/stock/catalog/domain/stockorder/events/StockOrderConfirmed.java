package net.greeta.stock.catalog.domain.stockorder.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.shared.eventhandling.events.Event;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class StockOrderConfirmed extends Event {

    public StockOrderConfirmed(UUID orderId) {
        super(orderId);
    }
}
