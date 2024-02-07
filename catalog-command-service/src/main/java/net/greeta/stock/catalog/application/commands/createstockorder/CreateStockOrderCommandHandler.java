package net.greeta.stock.catalog.application.commands.createstockorder;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.application.models.StockOrderResponse;
import net.greeta.stock.catalog.domain.catalogitem.*;
import net.greeta.stock.catalog.domain.stockorder.StockOrderAggregate;
import net.greeta.stock.catalog.domain.stockorder.StockOrderAggregateRepository;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.catalog.CreateProductCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CreateStockOrderCommandHandler implements CatalogCommandHandler<StockOrderResponse, CreateStockOrderCommand> {
  private final StockOrderAggregateRepository stockOrderRepository;

  @Transactional
  @CommandHandler
  @Override
  public StockOrderResponse handle(CreateStockOrderCommand command) {

    final var stockOrderAggregate = stockOrderRepository.save(() -> stockOrderOf(command));

    return StockOrderResponse.builder()
        .orderId((UUID) stockOrderAggregate.identifier())
        .version(stockOrderAggregate.version())
        .build();
  }

  private StockOrderAggregate stockOrderOf(CreateStockOrderCommand command) {
    return new StockOrderAggregate(command.orderId(), command.stockOrderItems());
  }

}
