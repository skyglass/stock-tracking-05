package net.greeta.stock.catalog.application.commandbus;

import net.greeta.stock.common.domain.dto.base.Command;

public interface CatalogCommandBus {
  <R, C extends Command<R>> R execute(C command);
}
