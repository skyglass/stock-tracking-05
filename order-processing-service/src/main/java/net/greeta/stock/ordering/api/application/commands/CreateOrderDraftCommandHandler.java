package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.ordering.api.application.dtos.OrderDraftDTO;
import net.greeta.stock.ordering.api.application.models.BasketItem;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.Order;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.Price;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.Product;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.Units;
import net.greeta.stock.ordering.shared.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@CommandHandler
@RequiredArgsConstructor
public class CreateOrderDraftCommandHandler implements Command.Handler<CreateOrderDraftCommand, OrderDraftDTO> {
  @Transactional
  @Override
  public OrderDraftDTO handle(CreateOrderDraftCommand message) {
    final var order = Order.newDraft();
    final var orderItems = message.items()
        .stream()
        .map(BasketItem::toOrderItemDTO)
        .collect(Collectors.toList());

    orderItems.forEach(item -> order.addOrderItem(
        Product.builder()
            .productId(item.productId())
            .productName(item.productName())
            .pictureUrl(item.pictureUrl())
            .unitPrice(Price.of(item.unitPrice()))
            .discount(Price.of(item.discount()))
            .units(Units.of(item.units()))
            .build()
    ));

    return OrderDraftDTO.fromOrder(order);
  }
}
