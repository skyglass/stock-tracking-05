package net.greeta.stock.catalogquery.application.queries.catalogitemsbyids;

import net.greeta.stock.catalogquery.application.querybus.Query;
import net.greeta.stock.catalogquery.model.CatalogItem;

import java.util.Set;
import java.util.UUID;

public record CatalogItemsByIdsQuery(
    Set<UUID> catalogItemIds
) implements Query<Iterable<CatalogItem>> {
}
