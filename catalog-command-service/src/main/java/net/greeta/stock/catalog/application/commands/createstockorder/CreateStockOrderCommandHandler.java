package net.greeta.stock.catalog.application.commands.createstockorder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.application.events.StockOrderCreated;
import net.greeta.stock.catalog.application.integrationevents.events.StockOrderItem;
import net.greeta.stock.catalog.application.models.StockOrderResponse;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItem;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItemRepository;
import net.greeta.stock.catalog.domain.stockorder.StockOrderAggregate;
import net.greeta.stock.catalog.domain.stockorder.StockOrderAggregateRepository;
import net.greeta.stock.catalog.application.query.model.StockOrderItemStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class CreateStockOrderCommandHandler implements CatalogCommandHandler<StockOrderResponse, CreateStockOrderCommand> {
  private final StockOrderAggregateRepository stockOrderRepository;
  private final QueryStockOrderItemRepository stockOrderItemRepository;

  @CommandHandler
  @Override
  @Transactional("mongoTransactionManager")
  public StockOrderResponse handle(CreateStockOrderCommand command) {

    final var stockOrderAggregate = stockOrderRepository.save(() -> stockOrderOf(command));

    return StockOrderResponse.builder()
        .orderId((UUID) stockOrderAggregate.identifier())
        .version(stockOrderAggregate.version())
        .build();
  }

  @EventHandler
  @Transactional("transactionManager")
  public void on(StockOrderCreated event) {
    log.info("Handling event: {} ({})", event.getId(), event.getClass().getSimpleName());

    for (StockOrderItem stockOrderItem : event.getStockOrderItems()) {
      final var queryStockOrderItem = QueryStockOrderItem.builder()
              .id(UUID.randomUUID())
              .orderId(event.getId())
              .productId(stockOrderItem.getProductId())
              .quantity(stockOrderItem.getUnits())
              .stockOrderItemStatus(StockOrderItemStatus.AwaitingConfirmation)
              .build();

      stockOrderItemRepository.save(queryStockOrderItem);
    }
  }

  private StockOrderAggregate stockOrderOf(CreateStockOrderCommand command) {
    return new StockOrderAggregate(command.orderId(), command.stockOrderItems());
  }

}
