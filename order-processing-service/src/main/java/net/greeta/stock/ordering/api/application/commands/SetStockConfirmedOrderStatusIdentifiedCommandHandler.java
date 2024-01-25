package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.ordering.shared.CommandHandler;

@CommandHandler
public class SetStockConfirmedOrderStatusIdentifiedCommandHandler
    implements Command.Handler<SetStockConfirmedOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetStockConfirmedOrderStatusIdentifiedCommand setStockConfirmedOrderStatusIdentifiedCommand) {
    return true;
  }
}
