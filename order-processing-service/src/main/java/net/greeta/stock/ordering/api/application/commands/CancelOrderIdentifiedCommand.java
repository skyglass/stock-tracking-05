package net.greeta.stock.ordering.api.application.commands;

import net.greeta.stock.shared.eventhandling.commands.IdentifiedCommand;

import java.util.UUID;

public class CancelOrderIdentifiedCommand extends IdentifiedCommand<CancelOrderCommand, Boolean> {
  public CancelOrderIdentifiedCommand(CancelOrderCommand command, UUID id) {
    super(command, id);
  }
}
