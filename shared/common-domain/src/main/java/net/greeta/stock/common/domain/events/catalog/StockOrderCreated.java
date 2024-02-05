package net.greeta.stock.common.domain.events.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.common.domain.dto.catalog.StockOrderItem;

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
