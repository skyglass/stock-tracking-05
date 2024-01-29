package net.greeta.stock.catalog.application.commandbus;

import net.greeta.stock.common.domain.dto.base.Command;

public interface CatalogCommandHandler<R, C extends Command<R>> {
  R handle(C command);
}
