package net.greeta.stock.ordering.api.application.commands;

import net.greeta.stock.shared.eventhandling.commands.IdentifiedCommand;

import java.util.UUID;

public class SetStockConfirmedOrderStatusIdentifiedCommand extends
        IdentifiedCommand<SetStockConfirmedOrderStatusCommand, Boolean> {
  public SetStockConfirmedOrderStatusIdentifiedCommand(SetStockConfirmedOrderStatusCommand command, UUID id) {
    super(command, id);
  }
}
