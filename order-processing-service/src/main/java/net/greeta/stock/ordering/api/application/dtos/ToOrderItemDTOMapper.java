package net.greeta.stock.ordering.api.application.dtos;

import net.greeta.stock.common.domain.dto.order.OrderBasketItem;
import net.greeta.stock.common.domain.dto.order.OrderItemDTO;

public class ToOrderItemDTOMapper {
  public OrderItemDTO map(OrderBasketItem basketItem) {
    return new OrderItemDTO(
        basketItem.getProductId(),
        basketItem.getProductName(),
        basketItem.getUnitPrice(),
        0D, // basketItem.getDiscount(),
        basketItem.getQuantity(),
        basketItem.getPictureUrl()
    );
  }
}
