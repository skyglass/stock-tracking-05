package net.greeta.stock.ordering.api.application.dtos;

import net.greeta.stock.common.domain.dto.basket.BasketItem;
import net.greeta.stock.common.domain.dto.order.OrderItemDTO;

public class ToOrderItemDTOMapper {
  public OrderItemDTO map(BasketItem basketItem) {
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
