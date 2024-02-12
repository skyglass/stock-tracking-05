package net.greeta.stock.catalog.application.commands.addstocknotifyorders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.application.commands.removestock.RemoveStockCommand;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItem;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItemRepository;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemAggregateRepository;
import net.greeta.stock.catalog.application.query.model.StockOrderItemStatus;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.shared.rest.error.NotFoundException;
import org.axonframework.commandhandling.CommandHandler;
import org.hibernate.Remove;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
@Slf4j
public class AddStockNotifyOrdersCommandHandler implements CatalogCommandHandler<CatalogItemResponse, AddStockNotifyOrdersCommand> {
  private final CatalogItemAggregateRepository catalogItemRepository;
  private final CatalogCommandBus commandBus;

  @CommandHandler
  @Override
  @Transactional("mongoTransactionManager")
  public CatalogItemResponse handle(AddStockNotifyOrdersCommand command) {
    final var catalogItem = catalogItemRepository.loadAggregate(command.productId());

    if (isNull(catalogItem)) {
      throw new NotFoundException("Catalog Item not found: productId = %s".formatted(command.productId()));
    }

    for (RemoveStockCommand removeStockCommand: command.removeStockCommands()) {
      log.info("AddStockNotifyOrdersCommandHandler.RemoveStockCommand started for order {} and product {} with quantity {}",
              removeStockCommand.getOrderId(), command.productId(), removeStockCommand.getQuantity());
      commandBus.execute(removeStockCommand);
    }

    return CatalogItemResponse.builder()
        .productId(command.productId())
        .version(catalogItem.version())
        .build();
  }
}
