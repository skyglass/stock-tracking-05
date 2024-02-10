package net.greeta.stock.catalog.application.commands.addstocknotifyorders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.application.commands.removestock.RemoveStockCommand;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItem;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItemRepository;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemAggregateRepository;
import net.greeta.stock.catalog.domain.stockorder.StockOrderItemStatus;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.shared.rest.error.NotFoundException;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
@Slf4j
public class AddStockNotifyOrdersCommandHandler implements CatalogCommandHandler<CatalogItemResponse, AddStockNotifyOrdersCommand> {
  private final CatalogItemAggregateRepository catalogItemRepository;
  private final QueryStockOrderItemRepository stockOrderItemRepository;
  private final CatalogCommandBus commandBus;

  @CommandHandler
  @Override
  @Transactional("mongoTransactionManager")
  public CatalogItemResponse handle(AddStockNotifyOrdersCommand command) {
    final var catalogItem = catalogItemRepository.loadAggregate(command.productId());

    if (isNull(catalogItem)) {
      throw new NotFoundException("Catalog Item not found: productId = %s".formatted(command.productId()));
    }

    var stockOrderItems = stockOrderItemRepository
            .findAllByProductIdAndStockOrderItemStatus(command.productId(), StockOrderItemStatus.AwaitingConfirmation);
    int stockQuantity = command.availableStock().intValue();
    if (stockOrderItems.isPresent()) {
      for (QueryStockOrderItem stockOrderItem: stockOrderItems.get()) {
        if (stockQuantity >= stockOrderItem.getQuantity().intValue()) {
          RemoveStockCommand removeStockCommand = new RemoveStockCommand(
                  command.productId(), stockOrderItem.getOrderId(),
                  stockOrderItem.getQuantity());
          log.info("AddStockNotifyOrdersCommandHandler.RemoveStockCommand started for order {} and product {} with quantity {}", stockOrderItem.getOrderId(), command.productId(), stockOrderItem.getQuantity());
          commandBus.execute(removeStockCommand);
          stockQuantity -= stockOrderItem.getQuantity().intValue();
        }
        if (stockQuantity == 0) {
          break;
        }
      }
    }

    return CatalogItemResponse.builder()
        .productId(command.productId())
        .version(catalogItem.version())
        .build();
  }
}
