package net.greeta.stock.catalog.application.commandbus;

import net.greeta.stock.common.domain.dto.Command;

public interface CatalogCommandHandler<R, C extends Command<R>> {
  R handle(C command);
}
