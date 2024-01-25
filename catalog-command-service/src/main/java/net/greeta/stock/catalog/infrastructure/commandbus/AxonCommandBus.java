package net.greeta.stock.catalog.infrastructure.commandbus;

import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.commandbus.Command;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AxonCommandBus implements CatalogCommandBus {
  private final CommandGateway commandGateway;

  @Override
  public <R, C extends Command<R>> R execute(C command) {
    return commandGateway.sendAndWait(command);
  }
}
