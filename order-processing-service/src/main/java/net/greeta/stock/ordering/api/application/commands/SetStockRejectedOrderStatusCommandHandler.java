package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.common.domain.dto.order.OrderId;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderRepository;
import net.greeta.stock.shared.eventhandling.commands.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

@CommandHandler
@RequiredArgsConstructor
public class SetStockRejectedOrderStatusCommandHandler implements Command.Handler<SetStockRejectedOrderStatusCommand, Boolean> {
  private final OrderRepository orderRepository;

  /**
   * Handler which processes the command when Stock service rejects the request.
   */
  @SneakyThrows
  @Override
  @Transactional
  public Boolean handle(SetStockRejectedOrderStatusCommand command) {

    final var orderToUpdate = orderRepository.findById(OrderId.of(command.orderNumber()))
        .orElse(null);
    if (orderToUpdate == null) {
      return false;
    }

    orderToUpdate.setCancelledStatusWhenStockIsRejected(command.orderStockItems());
    orderRepository.save(orderToUpdate);
    return true;
  }
}
