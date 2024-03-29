package net.greeta.stock.catalogquery.application.queries.catalogitembyid;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalogquery.model.CatalogItem;
import net.greeta.stock.catalogquery.model.CatalogItemRepository;
import net.greeta.stock.catalogquery.application.querybus.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CatalogItemByIdQueryHandler implements QueryHandler<Optional<CatalogItem>, CatalogItemByIdQuery> {

  private final CatalogItemRepository catalogItemRepository;

  @Override
  public Optional<CatalogItem> handle(CatalogItemByIdQuery query) {
    return catalogItemRepository.findById(query.catalogItemId());
  }
}
