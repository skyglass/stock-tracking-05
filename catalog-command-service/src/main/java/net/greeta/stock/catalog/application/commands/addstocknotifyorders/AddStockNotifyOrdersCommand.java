package net.greeta.stock.catalog.application.commands.addstocknotifyorders;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import net.greeta.stock.catalog.application.commands.removestock.RemoveStockCommand;
import net.greeta.stock.catalog.application.integrationevents.events.StockOrderItem;
import net.greeta.stock.common.domain.dto.base.Command;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;
import java.util.UUID;

public record AddStockNotifyOrdersCommand(
        @JsonProperty
        @NotNull(message = "No order id defined")
        @TargetAggregateIdentifier
        UUID productId,
        @JsonProperty
        @Valid
        @NotEmpty(message = "The command must contain at least one RemoveStockCommand")
        List<RemoveStockCommand> removeStockCommands
) implements Command<CatalogItemResponse> {
}
