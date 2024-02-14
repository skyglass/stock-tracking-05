package net.greeta.stock.catalog.application.commands.createstockorder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.application.models.StockOrderResponse;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItemRepository;
import net.greeta.stock.catalog.domain.stockorder.StockOrderAggregate;
import net.greeta.stock.catalog.domain.stockorder.StockOrderAggregateRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class CreateStockOrderCommandHandler implements CatalogCommandHandler<StockOrderResponse, CreateStockOrderCommand> {
  private final StockOrderAggregateRepository stockOrderRepository;

  @CommandHandler
  @Override
  public StockOrderResponse handle(CreateStockOrderCommand command) {
    log.info("CreateStockOrderCommandHandler.CreateStockOrderCommand started for order {}",
            command.orderId());

    var check = stockOrderRepository.load(command.orderId()).orElse(null);
    if (check != null) {
      return StockOrderResponse.builder()
              .orderId(command.orderId())
              .build();
    }

    var stockOrderAggregate = stockOrderRepository.save(() -> stockOrderOf(command));

    return StockOrderResponse.builder()
            .orderId((UUID) stockOrderAggregate.identifier())
            .version(stockOrderAggregate.version())
            .build();
  }

  private StockOrderAggregate stockOrderOf(CreateStockOrderCommand command) {
    return new StockOrderAggregate(command.orderId(), command.stockOrderItems());
  }

}
