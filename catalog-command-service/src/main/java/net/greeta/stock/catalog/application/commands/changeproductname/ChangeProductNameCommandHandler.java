package net.greeta.stock.catalog.application.commands.changeproductname;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandHandler;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemAggregateRepository;
import net.greeta.stock.catalog.domain.catalogitem.ProductName;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ChangeProductNameCommandHandler implements CatalogCommandHandler<CatalogItemResponse, ChangeProductNameCommand> {
  private final CatalogItemAggregateRepository catalogItemRepository;

  @CommandHandler
  @Transactional("mongoTransactionManager")
  public CatalogItemResponse handle(ChangeProductNameCommand command) {
    final var catalogItem = catalogItemRepository.loadAggregate(command.productId());

    catalogItem.execute(c -> c.changeName(ProductName.of(command.name())));

    return CatalogItemResponse.builder()
        .productId(command.productId())
        .version(catalogItem.version())
        .build();
  }
}
