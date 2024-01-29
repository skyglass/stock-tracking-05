package net.greeta.stock.common.domain.dto.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import net.greeta.stock.common.domain.dto.base.Command;

public record AddBrandCommand(
    @JsonProperty
    @NotNull String name
) implements Command<AddBrandResponse> {
}
