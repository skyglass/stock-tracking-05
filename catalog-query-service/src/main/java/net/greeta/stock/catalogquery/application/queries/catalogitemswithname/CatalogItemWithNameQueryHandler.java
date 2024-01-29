package net.greeta.stock.catalogquery.application.queries.catalogitemswithname;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalogquery.application.querybus.QueryHandler;
import net.greeta.stock.catalogquery.model.CatalogItemRepository;
import net.greeta.stock.catalogquery.model.CatalogItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CatalogItemWithNameQueryHandler implements QueryHandler<Page<CatalogItem>, CatalogItemWithNameQuery> {

  private final CatalogItemRepository catalogItemRepository;

  @Override
  public Page<CatalogItem> handle(CatalogItemWithNameQuery query) {
    final var page = PageRequest.of(query.pageIndex(), query.pageSize());
    return catalogItemRepository.findAllByName(query.name(), page);
  }
}
