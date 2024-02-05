package net.greeta.stock.common.domain.dto.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import net.greeta.stock.common.domain.dto.base.Command;

import java.util.List;
import java.util.UUID;

public record CreateStockOrderCommand(
        @JsonProperty
        @NotNull(message = "No order id defined")
        UUID orderId,
        @JsonProperty
        @NotNull
        List<StockOrderItem> stockOrderItems
) implements Command<StockOrderResponse> {
}
