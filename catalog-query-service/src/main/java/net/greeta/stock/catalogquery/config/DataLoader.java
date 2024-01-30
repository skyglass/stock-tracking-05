package net.greeta.stock.catalogquery.config;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalogquery.model.CatalogItem;
import net.greeta.stock.catalogquery.model.CatalogItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Insert test data.
 */
@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
  private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

  private final CatalogItemRepository catalogItemRepository;

  @Override
  public void run(ApplicationArguments args) {
    logger.info("Inserting test data...");

    List<CatalogItem> catalogItems = List.of(
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(10)
            .description("Some adidas terrex shoes...")
            .name("Adidas terrex")
            .price(60.0)
            .pictureFileName("adidas-shoes-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(13)
            .description("Adidas terrex-2 shoes...")
            .name("Adidas terrex-2")
            .price(43.5)
            .pictureFileName("adidas-shoes-2.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(2)
            .description("Adidas shoes...")
            .name("Adidas sh")
            .price(51.2)
            .pictureFileName("adidas-shoes-3.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(60)
            .description("Adidas shoes new...")
            .name("Adidas sport")
            .price(31.7)
            .pictureFileName("adidas-shoes-4.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(35)
            .description("Giro shoes...")
            .name("Giro bike shoes")
            .price(50.2)
            .pictureFileName("giro-shoes-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(1)
            .description("Giro street shoes...")
            .name("Giro street")
            .price(34.8)
            .pictureFileName("giro-shoes-2.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(10)
            .description("Giro vibram shoes...")
            .name("Giro vibram")
            .price(90.0)
            .pictureFileName("giro-shoes-3.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(10)
            .description("Etnies bike shoes...")
            .name("Etnies bike")
            .price(46.2)
            .pictureFileName("etnies-shoes-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(12)
            .description("Etnies street shoes...")
            .name("Etnies street")
            .price(37.9)
            .pictureFileName("etnies-shoes-2.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(40)
            .description("Endura shirt...")
            .name("Endura shirt")
            .price(10.9)
            .pictureFileName("endura-shirt-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(15)
            .description("Etnies shirt...")
            .name("Etnies street shirt")
            .price(10.5)
            .pictureFileName("etnies-shirt-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(54)
            .description("Etnies bike shirt...")
            .name("Etnies bike")
            .price(12.7)
            .pictureFileName("etnies-shirt-2.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(3)
            .description("Etnies street shirt description...")
            .name("Nike shoes")
            .price(16.4)
            .pictureFileName("etnies-shirt-3.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(16)
            .description("Fox classic shirt description...")
            .name("Fox classic")
            .price(20.0)
            .pictureFileName("fox-shirt-1.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(100)
            .description("Fox racing shirt description...")
            .name("Fox racing shirt")
            .price(21.25)
            .pictureFileName("fox-shirt-2.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(60)
            .description("Fox street shirt description...")
            .name("Fox street shirt")
            .price(13.5)
            .pictureFileName("fox-shirt-3.png")
            .build(),
        CatalogItem.builder()
            .id(UUID.randomUUID())
            .availableStock(5)
            .description("Something...")
            .name("Puma Shoes")
            .price(14.0)
            .pictureFileName("fox-shirt-4.png")
            .build()
    );
    catalogItemRepository.saveAll(catalogItems);
  }
}
