package net.greeta.stock.catalog.application.commands.addstocknotifyorders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.application.commandbus.CommandBusRetryHelper;
import net.greeta.stock.catalog.application.commands.removestock.RemoveStockCommand;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class AddStockNotifyOrdersCommandHandler implements CatalogCommandHandler<CatalogItemResponse, AddStockNotifyOrdersCommand> {
  private final CommandBusRetryHelper commandBusRetryHelper;

  @CommandHandler
  @Override
  public CatalogItemResponse handle(AddStockNotifyOrdersCommand command) {
    for (RemoveStockCommand removeStockCommand: command.removeStockCommands()) {
      log.info("AddStockNotifyOrdersCommandHandler.RemoveStockCommand started for order {} and product {} with quantity {}",
              removeStockCommand.getOrderId(), command.productId(), removeStockCommand.getQuantity());
      commandBusRetryHelper.execute(removeStockCommand);
    }

    return CatalogItemResponse.builder()
        .productId(command.productId())
        .build();
  }
}
