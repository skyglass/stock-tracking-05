package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;

public record SetAwaitingValidationOrderStatusCommand(String orderNumber) implements Command<Boolean> {
}
