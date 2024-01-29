package net.greeta.stock.ordering.api.application.commands;

import net.greeta.stock.common.domain.dto.order.ShipOrderCommand;

import java.util.UUID;

public class ShipOrderIdentifiedCommand extends IdentifiedCommand<ShipOrderCommand, Boolean> {
  public ShipOrderIdentifiedCommand(ShipOrderCommand command, UUID id) {
    super(command, id);
  }
}
