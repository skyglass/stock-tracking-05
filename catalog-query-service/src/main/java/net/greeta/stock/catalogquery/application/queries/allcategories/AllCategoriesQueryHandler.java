package net.greeta.stock.catalogquery.application.queries.allcategories;

import net.greeta.stock.catalogquery.application.querybus.QueryHandler;
import net.greeta.stock.catalogquery.model.Category;
import net.greeta.stock.catalogquery.model.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AllCategoriesQueryHandler implements QueryHandler<Iterable<Category>, AllCategoryQuery> {

  private final CategoryRepository categoryRepository;

  @Override
  public Iterable<Category> handle(AllCategoryQuery query) {
    return categoryRepository.findAll();
  }
}
