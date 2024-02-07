package net.greeta.stock.common.domain.dto.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.base.Command;

;

public record CreateProductCommand(
    @JsonProperty
    @NotNull(message = "No product name defined")
    String name,
    @JsonProperty
    String description,
    @JsonProperty
    @NotNull
    Double price,
    @JsonProperty
    String pictureFileName,
    @NotNull
    @JsonProperty
    Integer availableStock
) implements Command<CatalogItemResponse> {
}
