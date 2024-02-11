package net.greeta.stock.shared.eventhandling.commands;

import an.awesome.pipelinr.Command;

public interface IdempotentCommandBus {
  <R, C extends Command<R>> R send(C command);
}
