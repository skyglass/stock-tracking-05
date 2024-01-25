package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import net.greeta.stock.ordering.shared.CommandHandler;

@CommandHandler
public class SetAwaitingValidationIdentifiedOrderStatusCommandHandler
    implements Command.Handler<SetAwaitingValidationOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetAwaitingValidationOrderStatusIdentifiedCommand setAwaitingValidationOrderStatusIdentifiedCommand) {
    return true;
  }
}
