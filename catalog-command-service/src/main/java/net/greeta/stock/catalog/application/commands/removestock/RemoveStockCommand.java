package net.greeta.stock.catalog.application.commands.removestock;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import net.greeta.stock.common.domain.dto.base.Command;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class RemoveStockCommand implements Command<CatalogItemResponse> {
    @JsonProperty
    @NotNull
    private UUID productId;

    @JsonProperty
    @NotNull
    @TargetAggregateIdentifier
    private UUID orderId;

    @JsonProperty
    @NotNull @Min(1)
    private Integer quantity;
}