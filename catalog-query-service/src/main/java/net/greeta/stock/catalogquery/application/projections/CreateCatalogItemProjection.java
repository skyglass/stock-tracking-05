package net.greeta.stock.catalogquery.application.projections;

import net.greeta.stock.catalog.shared.events.CatalogItemCreated;
import net.greeta.stock.catalog.shared.model.BrandDto;
import net.greeta.stock.catalog.shared.model.CategoryDto;
import net.greeta.stock.catalogquery.model.*;
import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalogquery.model.*;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@ProcessingGroup("kafka-group")
@Component
public class CreateCatalogItemProjection {
  private static final Logger logger = LoggerFactory.getLogger(CreateCatalogItemProjection.class);

  private final CatalogItemRepository catalogItemRepository;
  private final BrandRepository brandRepository;
  private final CategoryRepository categoryRepository;

  @EventHandler
  @Transactional
  public void project(CatalogItemCreated event) {
    logger.info("Handling event: {} ({})", event.getId(), event.getClass().getSimpleName());

    final var brand = findOrCreate(event.getBrand());
    final var category = findOrCreate(event.getCategory());
    final var catalogItem = CatalogItem.builder()
        .id(event.getId())
        .pictureFileName(event.getPictureFileName())
        .availableStock(event.getAvailableStock())
        .description(event.getDescription())
        .price(event.getPrice())
        .name(event.getName())
        .brand(brand)
        .category(category)
        .build();

    catalogItemRepository.save(catalogItem);
  }

  private Brand findOrCreate(BrandDto brand) {
    return brandRepository.findById(brand.getId())
        .orElseGet(() -> brandRepository.save(Brand.builder()
            .id(brand.getId())
            .name(brand.getName())
            .build()));
  }

  private Category findOrCreate(CategoryDto category) {
    return categoryRepository.findById(category.getId())
        .orElseGet(() -> categoryRepository.save(Category.builder()
            .id(category.getId())
            .name(category.getName())
            .build()));
  }

}
