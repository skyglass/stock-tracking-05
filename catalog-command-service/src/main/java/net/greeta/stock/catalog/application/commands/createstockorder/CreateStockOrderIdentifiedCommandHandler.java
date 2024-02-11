package net.greeta.stock.catalog.application.commands.createstockorder;

import an.awesome.pipelinr.Command;
import net.greeta.stock.shared.eventhandling.commands.CommandHandler;

@CommandHandler
public class CreateStockOrderIdentifiedCommandHandler implements Command.Handler<CreateStockOrderIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(CreateStockOrderIdentifiedCommand createOrderIdentifiedCommand) {
    return true; // Ignore duplicate requests for creating an order.
  }
}
