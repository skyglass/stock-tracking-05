package net.greeta.stock.catalog.application.commands.removestock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemAggregateRepository;
import net.greeta.stock.catalog.domain.stockorder.StockOrderAggregateRepository;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.shared.eventhandling.events.StockRemoved;
import net.greeta.stock.shared.rest.error.NotFoundException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
@Slf4j
public class RemoveStockCommandHandler implements CatalogCommandHandler<CatalogItemResponse, RemoveStockCommand> {
  private final CatalogItemAggregateRepository catalogItemRepository;
  private final StockOrderAggregateRepository stockOrderRepository;

  @CommandHandler
  public CatalogItemResponse handle(RemoveStockCommand command) {
    log.info("RemoveStockCommandHandler.RemoveStockCommand started for order {} and product {} with quantity {}",
            command.getOrderId(), command.getProductId(), command.getQuantity());
    final var catalogItem = catalogItemRepository.loadAggregate(command.getProductId());

    if (isNull(catalogItem)) {
        throw new NotFoundException("Catalog item not found");
    }
    catalogItem.execute(c -> c.removeStock(command));

    return CatalogItemResponse.builder()
            .productId(command.getProductId())
            .version(catalogItem.version())
            .build();
  }

  @EventHandler
  public void on(StockRemoved event) {
    log.info("RemoveStockCommandHandler.StockRemoved event handler started for order {} and product {} with quantity {}",
            event.getOrderId(), event.getId(), event.getQuantity());
    final var stockOrder = stockOrderRepository.loadAggregate(event.getOrderId());

    if (isNull(stockOrder)) {
      throw new NotFoundException("Stock Order %s not found".formatted(event.getOrderId()));
    }

    stockOrder.execute(c -> c.removeStockConfirmed(event));
  }
}
