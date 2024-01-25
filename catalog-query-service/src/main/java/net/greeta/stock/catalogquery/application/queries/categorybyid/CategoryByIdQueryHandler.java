package net.greeta.stock.catalogquery.application.queries.categorybyid;

import net.greeta.stock.catalogquery.application.querybus.QueryHandler;
import net.greeta.stock.catalogquery.model.Category;
import net.greeta.stock.catalogquery.model.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CategoryByIdQueryHandler implements QueryHandler<Optional<Category>, CategoryByIdQuery> {

  private final CategoryRepository categoryRepository;

  @Override
  public Optional<Category> handle(CategoryByIdQuery query) {
    return categoryRepository.findById(query.categoryId());
  }
}
