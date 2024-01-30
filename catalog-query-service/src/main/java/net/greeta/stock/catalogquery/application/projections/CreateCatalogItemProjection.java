package net.greeta.stock.catalogquery.application.projections;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.shared.events.CatalogItemCreated;
import net.greeta.stock.catalogquery.model.CatalogItem;
import net.greeta.stock.catalogquery.model.CatalogItemRepository;
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

  @EventHandler
  @Transactional
  public void project(CatalogItemCreated event) {
    logger.info("Handling event: {} ({})", event.getId(), event.getClass().getSimpleName());

    final var catalogItem = CatalogItem.builder()
        .id(event.getId())
        .pictureFileName(event.getPictureFileName())
        .availableStock(event.getAvailableStock())
        .description(event.getDescription())
        .price(event.getPrice())
        .name(event.getName())
        .build();

    catalogItemRepository.save(catalogItem);
  }

}
