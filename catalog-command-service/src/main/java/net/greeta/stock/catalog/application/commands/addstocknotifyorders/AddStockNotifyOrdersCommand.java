package net.greeta.stock.catalog.application.commands.addstocknotifyorders;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import net.greeta.stock.common.domain.dto.base.Command;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record AddStockNotifyOrdersCommand(
        @JsonProperty
        @NotNull(message = "No order id defined")
        @TargetAggregateIdentifier
        UUID productId,
        @JsonProperty
        @NotNull @Min(1) Integer availableStock
) implements Command<CatalogItemResponse> {
}
