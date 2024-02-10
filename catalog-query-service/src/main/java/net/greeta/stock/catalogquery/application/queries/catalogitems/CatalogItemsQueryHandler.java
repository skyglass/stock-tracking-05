package net.greeta.stock.catalogquery.application.queries.catalogitems;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalogquery.model.CatalogItem;
import net.greeta.stock.catalogquery.model.CatalogItemRepository;
import net.greeta.stock.catalogquery.application.querybus.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CatalogItemsQueryHandler implements QueryHandler<Page<CatalogItem>, CatalogItemsQuery> {

  private final CatalogItemRepository catalogItemRepository;

  @Override
  public Page<CatalogItem> handle(CatalogItemsQuery query) {
    final var page = PageRequest.of(query.pageIndex(), query.pageSize());

    return catalogItemRepository.findAll(
        page.withSort(Sort.sort(CatalogItem.class).by(CatalogItem::getId).descending())
    );
  }

}
