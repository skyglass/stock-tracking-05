package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.shared.eventhandling.commands.CommandHandler;

@CommandHandler
public class SetPaidIdentifiedOrderStatusCommandHandler implements Command.Handler<SetPaidOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetPaidOrderStatusIdentifiedCommand setPaidOrderStatusIdentifiedCommand) {
    return true;
  }
}
