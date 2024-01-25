package net.greeta.stock.catalogquery.application.queries.brandbyid;

import net.greeta.stock.catalogquery.application.querybus.Query;
import net.greeta.stock.catalogquery.model.Brand;

import java.util.Optional;
import java.util.UUID;

public record BrandByIdQuery(
    UUID brandId
) implements Query<Optional<Brand>> {
}
