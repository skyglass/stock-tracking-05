package net.greeta.stock.catalog.application.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.shared.eventhandling.events.Event;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RemoveStockRejected extends Event {
    @JsonProperty
    @NotNull
    private UUID orderId;
    @JsonProperty
    @NotNull @Min(1)
    private Integer quantity;
    @JsonProperty
    @NotNull @Min(0)
    private Integer availableStock;

    public RemoveStockRejected(UUID productId, UUID orderId, Integer quantity, Integer availableStock) {
        super(productId);
        this.orderId = orderId;
        this.quantity = quantity;
        this.availableStock = availableStock;
    }
}
