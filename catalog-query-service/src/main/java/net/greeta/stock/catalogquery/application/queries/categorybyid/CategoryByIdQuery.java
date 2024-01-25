package net.greeta.stock.catalogquery.application.queries.categorybyid;

import net.greeta.stock.catalogquery.application.querybus.Query;
import net.greeta.stock.catalogquery.model.Category;

import java.util.Optional;
import java.util.UUID;

public record CategoryByIdQuery(
    UUID categoryId
) implements Query<Optional<Category>> {
}
