package net.greeta.stock.catalogquery.application.queries.allbrands;

import net.greeta.stock.catalogquery.application.querybus.QueryHandler;
import net.greeta.stock.catalogquery.model.Brand;
import net.greeta.stock.catalogquery.model.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AllBrandsQueryHandler implements QueryHandler<Iterable<Brand>, AllBrandsQuery> {

  private final BrandRepository brandRepository;

  @Override
  public Iterable<Brand> handle(AllBrandsQuery query) {
    return brandRepository.findAll();
  }
}
