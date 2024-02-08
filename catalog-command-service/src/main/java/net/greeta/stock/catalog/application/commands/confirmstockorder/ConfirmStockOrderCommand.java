package net.greeta.stock.catalog.application.commands.confirmstockorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import net.greeta.stock.common.domain.dto.base.Command;
import net.greeta.stock.catalog.application.models.StockOrderResponse;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class ConfirmStockOrderCommand implements Command<StockOrderResponse> {

    @JsonProperty
    @NotNull
    @TargetAggregateIdentifier
    private UUID orderId;
}
