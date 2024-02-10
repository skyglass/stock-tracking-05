package net.greeta.stock.catalogquery.application.queries.catalogitems;

import net.greeta.stock.catalogquery.model.CatalogItem;
import net.greeta.stock.catalogquery.application.querybus.Query;
import org.springframework.data.domain.Page;

public record CatalogItemsQuery(
    Integer pageSize,
    Integer pageIndex
) implements Query<Page<CatalogItem>> {
}
