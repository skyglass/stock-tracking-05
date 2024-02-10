package net.greeta.stock.catalogquery.application.queries.catalogitembyid;

import net.greeta.stock.catalogquery.model.CatalogItem;
import net.greeta.stock.catalogquery.application.querybus.Query;

import java.util.Optional;
import java.util.UUID;

public record CatalogItemByIdQuery(
    UUID catalogItemId
) implements Query<Optional<CatalogItem>> {
}
