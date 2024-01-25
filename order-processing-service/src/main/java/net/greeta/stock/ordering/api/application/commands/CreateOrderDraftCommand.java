package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.ordering.api.application.dtos.OrderDraftDTO;
import net.greeta.stock.ordering.api.application.models.BasketItem;
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
    List<BasketItem> items
) implements Command<OrderDraftDTO> {
}
