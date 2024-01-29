package net.greeta.stock.common.domain.dto.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import net.greeta.stock.common.domain.dto.base.Command;

import java.util.UUID;

public record RemoveStockCommand(
    @JsonProperty
    @NotNull
    UUID productId,
    @JsonProperty
    @NotNull @Min(1)
    Integer quantity
) implements Command<CatalogItemResponse> {
}
