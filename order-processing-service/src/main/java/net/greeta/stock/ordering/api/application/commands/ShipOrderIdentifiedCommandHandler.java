package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.shared.eventhandling.commands.CommandHandler;

@CommandHandler
public class ShipOrderIdentifiedCommandHandler implements Command.Handler<ShipOrderIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(ShipOrderIdentifiedCommand shipOrderCommand) {
    return null; // Ignore duplicate requests for shipping order.
  }
}
