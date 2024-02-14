package net.greeta.stock.catalog.application.events.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.commandbus.CommandBusRetryHelper;
import net.greeta.stock.catalog.application.commands.addstocknotifyorders.AddStockNotifyOrdersCommand;
import net.greeta.stock.catalog.application.commands.removestock.RemoveStockCommand;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItem;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItemRepository;
import net.greeta.stock.catalog.application.query.model.StockOrderItemStatus;
import net.greeta.stock.shared.eventhandling.events.StockAdded;
import org.apache.commons.collections4.CollectionUtils;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class AddStockEventHandler {
  private final CommandBusRetryHelper commandBusRetryHelper;
  private final QueryStockOrderItemRepository stockOrderItemRepository;

  @EventHandler
  @Transactional("transactionManager")
  public void on(StockAdded event) {
    log.info("AddStockEventHandler.StockAdded event handler started for product {} with available stock {}",
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
      commandBusRetryHelper.execute(command);
    }
  }
}
