package net.greeta.stock.catalogquery.application.queries.brandbyid;

import net.greeta.stock.catalogquery.application.querybus.QueryHandler;
import net.greeta.stock.catalogquery.model.Brand;
import net.greeta.stock.catalogquery.model.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BrandByIdQueryHandler implements QueryHandler<Optional<Brand>, BrandByIdQuery> {

  private final BrandRepository brandRepository;

  @Override
  public Optional<Brand> handle(BrandByIdQuery query) {
    return brandRepository.findById(query.brandId());
  }
}
