package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.common.domain.dto.order.OrderId;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderRepository;
import net.greeta.stock.ordering.shared.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

@CommandHandler
@RequiredArgsConstructor
public class SetStockConfirmedOrderStatusCommandHandler implements Command.Handler<SetStockConfirmedOrderStatusCommand, Boolean> {
  private final OrderRepository orderRepository;

  /**
   * Handler which processes the command when Stock service confirms the request.
   */
  @SneakyThrows
  @Override
  @Transactional
  public Boolean handle(SetStockConfirmedOrderStatusCommand command) {

    final var orderToUpdate = orderRepository.findById(OrderId.of(command.orderNumber())).orElse(null);
    if (orderToUpdate == null) {
      return false;
    }

    orderToUpdate.setStockConfirmedStatus();
    orderRepository.save(orderToUpdate);
    return true;
  }
}
