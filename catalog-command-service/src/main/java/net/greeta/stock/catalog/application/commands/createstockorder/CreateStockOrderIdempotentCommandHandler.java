package net.greeta.stock.catalog.application.commands.createstockorder;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.commandbus.CommandBusRetryHelper;
import net.greeta.stock.shared.eventhandling.commands.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@CommandHandler
@RequiredArgsConstructor
@Slf4j
public class CreateStockOrderIdempotentCommandHandler implements Command.Handler<CreateStockOrderIdempotentCommand, Boolean> {

  private final CommandBusRetryHelper commandBusRetryHelper;

  @Override
  public Boolean handle(CreateStockOrderIdempotentCommand command) {
    log.info("CreateStockOrderIdempotentCommandHandler.CreateStockOrderIdempotentCommand started for order {}",
            command.orderId());

    CreateStockOrderCommand createStockOrderCommand = new CreateStockOrderCommand(
            command.orderId(), command.stockOrderItems());

    commandBusRetryHelper.execute(createStockOrderCommand);

    return true;
  }


}
