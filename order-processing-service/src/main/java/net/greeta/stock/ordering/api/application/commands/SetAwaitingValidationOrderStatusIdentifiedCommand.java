package net.greeta.stock.ordering.api.application.commands;

import net.greeta.stock.shared.eventhandling.commands.IdentifiedCommand;

import java.util.UUID;

public class SetAwaitingValidationOrderStatusIdentifiedCommand
    extends IdentifiedCommand<SetAwaitingValidationOrderStatusCommand, Boolean> {
  public SetAwaitingValidationOrderStatusIdentifiedCommand(SetAwaitingValidationOrderStatusCommand command, UUID id) {
    super(command, id);
  }
}
