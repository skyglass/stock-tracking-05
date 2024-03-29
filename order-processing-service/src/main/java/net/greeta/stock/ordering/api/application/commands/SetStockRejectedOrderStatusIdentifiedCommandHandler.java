package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.shared.eventhandling.commands.CommandHandler;

@CommandHandler
public class SetStockRejectedOrderStatusIdentifiedCommandHandler
    implements Command.Handler<SetStockRejectedOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetStockRejectedOrderStatusIdentifiedCommand setStockRejectedOrderStatusIdentifiedCommand) {
    return true;
  }
}
