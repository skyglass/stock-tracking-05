package net.greeta.stock.catalog.application.commands.addstock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.application.commands.addstocknotifyorders.AddStockNotifyOrdersCommand;
import net.greeta.stock.catalog.application.commands.removestock.RemoveStockCommand;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItem;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItemRepository;
import net.greeta.stock.catalog.application.query.model.StockOrderItemStatus;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemAggregateRepository;
import net.greeta.stock.catalog.domain.catalogitem.Units;
import net.greeta.stock.common.domain.dto.catalog.AddStockCommand;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.shared.eventhandling.events.StockAdded;
import net.greeta.stock.shared.rest.error.NotFoundException;
import org.apache.commons.collections4.CollectionUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
@Slf4j
public class AddStockCommandHandler implements CatalogCommandHandler<CatalogItemResponse, AddStockCommand> {
  private final CatalogItemAggregateRepository catalogItemRepository;
  private final CatalogCommandBus commandBus;
  private final QueryStockOrderItemRepository stockOrderItemRepository;

  @CommandHandler
  @Override
  @Transactional("mongoTransactionManager")
  public CatalogItemResponse handle(AddStockCommand command) {
    final var catalogItem = catalogItemRepository.loadAggregate(command.productId());

    if (isNull(catalogItem)) {
      throw new NotFoundException("Catalog item not found");
    }

    catalogItem.execute(c -> c.addStock(Units.of(command.quantity())));

    return CatalogItemResponse.builder()
        .productId(command.productId())
        .version(catalogItem.version())
        .build();
  }

  @EventHandler
  @Transactional("transactionManager")
  public void on(StockAdded event) {
    log.info("AddStockCommandHandler.StockAdded event handler started for product {} with available stock {}",
            event.getId(), event.getAvailableStock());

    List<RemoveStockCommand> removeStockCommands = new ArrayList<>();
    var stockOrderItems = stockOrderItemRepository
            .findAllByProductIdAndStockOrderItemStatus(event.getId(), StockOrderItemStatus.StockRejected);
    int stockQuantity = event.getAvailableStock().intValue();
    if (stockOrderItems.isPresent()) {
      for (QueryStockOrderItem stockOrderItem : stockOrderItems.get()) {
        if (stockQuantity >= stockOrderItem.getQuantity().intValue()) {
          RemoveStockCommand removeStockCommand = new RemoveStockCommand(
                  event.getId(), stockOrderItem.getOrderId(),
                  stockOrderItem.getQuantity());
          log.info("AddStockCommandHandler.StockAdded.RemoveStockCommand added for order {} and product {} with quantity {}", stockOrderItem.getOrderId(), event.getId(), stockOrderItem.getQuantity());
          stockOrderItem.setStockOrderItemStatus(StockOrderItemStatus.AwaitingConfirmation);
          stockOrderItemRepository.save(stockOrderItem);
          removeStockCommands.add(removeStockCommand);
          stockQuantity -= stockOrderItem.getQuantity().intValue();
        }
        if (stockQuantity == 0) {
          break;
        }
      }
    }

    if (CollectionUtils.isNotEmpty(removeStockCommands)) {
      AddStockNotifyOrdersCommand command = new AddStockNotifyOrdersCommand(event.getId(), removeStockCommands);
      commandBus.execute(command);
    }
  }
}
