package net.greeta.stock.catalog.application.commands.createstockorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import net.greeta.stock.catalog.application.integrationevents.events.StockOrderItem;
import net.greeta.stock.catalog.application.models.StockOrderResponse;
import net.greeta.stock.common.domain.dto.base.Command;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;
import java.util.UUID;

public record CreateStockOrderCommand(
        @JsonProperty
        @NotNull(message = "No order id defined")
        @TargetAggregateIdentifier
        UUID orderId,
        @JsonProperty
        @Valid
        @NotEmpty(message = "The order must contain at least one product")
        List<StockOrderItem> stockOrderItems
) implements Command<StockOrderResponse> {
}
