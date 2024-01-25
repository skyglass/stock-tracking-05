package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.ordering.shared.CommandHandler;

@CommandHandler
public class CreateOrderIdentifiedCommandHandler implements Command.Handler<CreateOrderIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(CreateOrderIdentifiedCommand createOrderIdentifiedCommand) {
    return true; // Ignore duplicate requests for creating an order.
  }
}
