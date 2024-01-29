package net.greeta.stock.ordering.api.application.commands;

import net.greeta.stock.common.domain.dto.order.CancelOrderCommand;

import java.util.UUID;

public class CancelOrderIdentifiedCommand extends IdentifiedCommand<CancelOrderCommand, Boolean> {
  public CancelOrderIdentifiedCommand(CancelOrderCommand command, UUID id) {
    super(command, id);
  }
}
