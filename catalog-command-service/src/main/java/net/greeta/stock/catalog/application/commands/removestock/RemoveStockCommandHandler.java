package net.greeta.stock.catalog.application.commands.removestock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemAggregateRepository;
import net.greeta.stock.catalog.domain.catalogitem.Units;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.catalog.RemoveStockCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class RemoveStockCommandHandler implements CatalogCommandHandler<CatalogItemResponse, RemoveStockCommand> {
  private final CatalogItemAggregateRepository catalogItemRepository;

  @CommandHandler
  public CatalogItemResponse handle(RemoveStockCommand command) {
    log.info("RemoveStockCommand started for order {} and product {} with quantity {}", command.orderId(), command.productId(), command.quantity());
    final var catalogItem = catalogItemRepository.loadAggregate(command.productId());
    catalogItem.execute(c -> c.removeStock(Units.of(command.quantity())));



    return CatalogItemResponse.builder()
        .productId(command.productId())
        .version(catalogItem.version())
        .build();
  }

}
