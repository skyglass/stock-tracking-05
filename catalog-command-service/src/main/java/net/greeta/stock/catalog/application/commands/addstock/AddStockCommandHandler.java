package net.greeta.stock.catalog.application.commands.addstock;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemRepository;
import net.greeta.stock.catalog.domain.catalogitem.Units;
import net.greeta.stock.common.domain.dto.catalog.AddStockCommand;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.shared.rest.error.NotFoundException;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
public class AddStockCommandHandler implements CatalogCommandHandler<CatalogItemResponse, AddStockCommand> {
  private final CatalogItemRepository catalogItemRepository;

  @CommandHandler
  @Override
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
}
