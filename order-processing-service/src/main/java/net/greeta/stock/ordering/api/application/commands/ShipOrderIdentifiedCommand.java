package net.greeta.stock.ordering.api.application.commands;

import net.greeta.stock.shared.eventhandling.commands.IdentifiedCommand;

import java.util.UUID;

public class ShipOrderIdentifiedCommand extends IdentifiedCommand<ShipOrderCommand, Boolean> {
  public ShipOrderIdentifiedCommand(ShipOrderCommand command, UUID id) {
    super(command, id);
  }
}
