package net.greeta.stock.catalogquery.application.queries.allbrands;

import net.greeta.stock.catalogquery.application.querybus.Query;
import net.greeta.stock.catalogquery.model.Brand;

public record AllBrandsQuery() implements Query<Iterable<Brand>> {
}
