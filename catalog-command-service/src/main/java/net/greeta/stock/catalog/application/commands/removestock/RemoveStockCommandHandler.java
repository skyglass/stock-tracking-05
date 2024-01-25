package net.greeta.stock.catalog.application.commands.removestock;

import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.application.models.CatalogItemResponse;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemRepository;
import net.greeta.stock.catalog.domain.catalogitem.Units;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RemoveStockCommandHandler implements CatalogCommandHandler<CatalogItemResponse, RemoveStockCommand> {
  private final CatalogItemRepository catalogItemRepository;

  @CommandHandler
  public CatalogItemResponse handle(RemoveStockCommand command) {
    final var catalogItem = catalogItemRepository.loadAggregate(command.productId());

    catalogItem.execute(c -> c.removeStock(Units.of(command.quantity())));

    return CatalogItemResponse.builder()
        .productId(command.productId())
        .version(catalogItem.version())
        .build();
  }
}
