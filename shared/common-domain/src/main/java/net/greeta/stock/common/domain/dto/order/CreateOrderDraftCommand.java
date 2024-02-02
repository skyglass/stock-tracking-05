package net.greeta.stock.common.domain.dto.order;

import an.awesome.pipelinr.Command;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderDraftCommand(
    @NotEmpty(message = "Buyer id is required")
    @JsonProperty("buyerId")
    String buyerId,
    @JsonProperty("items")
    @Valid
    @NotEmpty(message = "The basket must contain at least one product")
    List<OrderBasketItem> items
) implements Command<OrderDraftDTO> {
}
