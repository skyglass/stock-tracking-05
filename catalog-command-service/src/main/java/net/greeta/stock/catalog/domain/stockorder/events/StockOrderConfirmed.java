package net.greeta.stock.catalog.domain.stockorder.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.common.domain.dto.catalog.StockOrderItem;
import net.greeta.stock.common.domain.events.catalog.Event;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class StockOrderConfirmed extends Event {

    public StockOrderConfirmed(UUID orderId) {
        super(orderId);
    }
}
