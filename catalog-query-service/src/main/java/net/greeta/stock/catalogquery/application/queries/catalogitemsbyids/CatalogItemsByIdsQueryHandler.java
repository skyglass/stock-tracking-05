package net.greeta.stock.catalogquery.application.queries.catalogitemsbyids;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalogquery.model.CatalogItem;
import net.greeta.stock.catalogquery.model.CatalogItemRepository;
import net.greeta.stock.catalogquery.application.querybus.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CatalogItemsByIdsQueryHandler implements QueryHandler<Iterable<CatalogItem>, CatalogItemsByIdsQuery> {

  private final CatalogItemRepository catalogItemRepository;

  @Override
  public List<CatalogItem> handle(CatalogItemsByIdsQuery query) {
    return catalogItemRepository.findAllById(query.catalogItemIds());
  }
}
