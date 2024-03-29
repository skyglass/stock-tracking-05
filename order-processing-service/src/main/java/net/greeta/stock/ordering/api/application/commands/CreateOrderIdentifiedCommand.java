package net.greeta.stock.ordering.api.application.commands;

import net.greeta.stock.shared.eventhandling.commands.IdentifiedCommand;

import java.util.UUID;

public class CreateOrderIdentifiedCommand extends IdentifiedCommand<CreateOrderCommand, Boolean> {
  public CreateOrderIdentifiedCommand(CreateOrderCommand command, UUID id) {
    super(command, id);
  }
}
