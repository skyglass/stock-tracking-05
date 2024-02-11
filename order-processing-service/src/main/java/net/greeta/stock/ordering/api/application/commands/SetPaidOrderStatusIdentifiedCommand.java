package net.greeta.stock.ordering.api.application.commands;

import net.greeta.stock.shared.eventhandling.commands.IdentifiedCommand;

import java.util.UUID;

public class SetPaidOrderStatusIdentifiedCommand extends IdentifiedCommand<SetPaidOrderStatusCommand, Boolean> {
  public SetPaidOrderStatusIdentifiedCommand(SetPaidOrderStatusCommand command, UUID id) {
    super(command, id);
  }
}
