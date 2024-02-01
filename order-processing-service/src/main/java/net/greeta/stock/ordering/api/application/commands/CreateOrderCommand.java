package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;
import net.greeta.stock.common.domain.dto.order.BasketItem;
import net.greeta.stock.common.domain.dto.order.OrderItemDTO;
import net.greeta.stock.ordering.api.application.dtos.ToOrderItemDTOMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class CreateOrderCommand implements Command<Boolean> {
  private final List<OrderItemDTO> orderItems;
  private final String userId;
  private final String userName;
  private final UUID requestId;

  @Builder
  private CreateOrderCommand(
      List<BasketItem> basketItems,
      String userId,
      String userName,
      UUID requestId
  ) {
    orderItems = basketItems.stream()
        .map(basketItem -> new ToOrderItemDTOMapper().map(basketItem))
        .collect(Collectors.toList());
    this.userId = userId;
    this.userName = userName;
    this.requestId = requestId;
  }
}
