package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.common.domain.dto.order.OrderId;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderRepository;
import net.greeta.stock.ordering.shared.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@CommandHandler
@RequiredArgsConstructor
public class SetAwaitingValidationOrderStatusCommandHandler
    implements Command.Handler<SetAwaitingValidationOrderStatusCommand, Boolean> {
  private final OrderRepository orderRepository;

  /**
   * Handler which processes the command when order has been created
   */
  @Transactional
  @Override
  public Boolean handle(SetAwaitingValidationOrderStatusCommand command) {
    orderRepository.findById(OrderId.of(command.orderNumber()))
        .ifPresent(order -> {
          order.setAwaitingValidationStatus();
          orderRepository.save(order);
        });
    return true;
  }
}
