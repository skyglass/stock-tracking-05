package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record SetAwaitingValidationOrderStatusCommand(String orderNumber, UUID requestId) implements Command<Boolean> {
}
