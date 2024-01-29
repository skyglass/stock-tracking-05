package net.greeta.stock.common.domain.dto.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import net.greeta.stock.common.domain.dto.base.Command;

import java.util.UUID;

public record ChangeProductNameCommand(
    @JsonProperty
    @NotNull
    UUID productId,
    @JsonProperty
    @NotEmpty String name
) implements Command<CatalogItemResponse> {
}
