package net.greeta.stock.catalogquery.application.queries.allcategories;

import net.greeta.stock.catalogquery.application.querybus.Query;
import net.greeta.stock.catalogquery.model.Category;

public record AllCategoryQuery() implements Query<Iterable<Category>> {
}
