package net.greeta.stock.catalog.application.commands.createstockorder;

import net.greeta.stock.shared.eventhandling.commands.IdentifiedCommand;

import java.util.UUID;

public class CreateStockOrderIdentifiedCommand extends IdentifiedCommand<CreateStockOrderIdempotentCommand, Boolean> {
    public CreateStockOrderIdentifiedCommand(CreateStockOrderIdempotentCommand command, UUID id) {
        super(command, id);
    }
}