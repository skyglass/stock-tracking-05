package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.common.domain.dto.order.CancelOrderCommand;
import net.greeta.stock.common.domain.dto.order.Order;
import net.greeta.stock.common.domain.dto.order.OrderId;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderRepository;
import net.greeta.stock.ordering.shared.CommandHandler;
import net.greeta.stock.shared.rest.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@CommandHandler
@RequiredArgsConstructor
public class CancelOrderCommandHandler implements Command.Handler<CancelOrderCommand, Boolean> {
  private final OrderRepository orderRepository;

  /**
   * Handler which processes the command when customer executes cancel order from app.
   */
  @Override
  @Transactional
  public Boolean handle(CancelOrderCommand command) {
    final var order = findOrder(command.orderNumber());
    order.setCancelledStatus();
    orderRepository.save(order);

    return true;
  }

  private Order findOrder(String orderNumber) {
    return orderRepository.findById(OrderId.of(orderNumber))
        .orElseThrow(() -> new NotFoundException("Order %s not found".formatted(orderNumber)));
  }

}
