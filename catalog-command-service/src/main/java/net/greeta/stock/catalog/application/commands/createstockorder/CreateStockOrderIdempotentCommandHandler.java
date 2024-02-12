package net.greeta.stock.catalog.application.commands.createstockorder;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.shared.eventhandling.commands.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@CommandHandler
@RequiredArgsConstructor
public class CreateStockOrderIdempotentCommandHandler implements Command.Handler<CreateStockOrderIdempotentCommand, Boolean> {
  private static final Logger logger = LoggerFactory.getLogger(CreateStockOrderIdempotentCommandHandler.class);

  private final CatalogCommandBus commandBus;

  @Override
  @Transactional("mongoTransactionManager")
  public Boolean handle(CreateStockOrderIdempotentCommand command) {

    CreateStockOrderCommand createStockOrderCommand = new CreateStockOrderCommand(
            command.orderId(), command.stockOrderItems());

    commandBus.execute(createStockOrderCommand);

    return true;
  }


}
