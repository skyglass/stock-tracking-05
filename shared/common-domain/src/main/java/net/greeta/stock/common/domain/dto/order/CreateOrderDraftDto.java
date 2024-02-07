package net.greeta.stock.common.domain.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import net.greeta.stock.common.domain.dto.basket.BasketItem;

import java.util.List;

public record CreateOrderDraftDto(
    @NotEmpty(message = "Buyer id is required")
    @JsonProperty("buyerId")
    String buyerId,
    @JsonProperty("items")
    @Valid
    @NotEmpty(message = "The basket must contain at least one product")
    List<BasketItem> items
) {
}
