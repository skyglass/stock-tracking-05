package net.greeta.stock.catalogquery.application.queries.catalogitems;

import net.greeta.stock.catalogquery.application.querybus.Query;
import net.greeta.stock.catalogquery.model.CatalogItem;
import org.springframework.data.domain.Page;

import java.util.UUID;

public record CatalogItemsQuery(
    Integer pageSize,
    Integer pageIndex
) implements Query<Page<CatalogItem>> {
}
