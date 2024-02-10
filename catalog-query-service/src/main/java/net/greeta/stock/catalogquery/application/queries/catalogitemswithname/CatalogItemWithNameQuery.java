package net.greeta.stock.catalogquery.application.queries.catalogitemswithname;

import net.greeta.stock.catalogquery.model.CatalogItem;
import net.greeta.stock.catalogquery.application.querybus.Query;
import org.springframework.data.domain.Page;

public record CatalogItemWithNameQuery(
    Integer pageSize,
    Integer pageIndex,
    String name
) implements Query<Page<CatalogItem>> {
}
