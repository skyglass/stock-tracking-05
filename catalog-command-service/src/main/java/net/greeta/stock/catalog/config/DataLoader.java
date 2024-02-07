package net.greeta.stock.catalog.config;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.common.domain.dto.catalog.CreateProductCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Insert test data.
 */
@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
  private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

  private final CatalogCommandBus commandBus;

  @Override
  public void run(ApplicationArguments args) {
    logger.info("Inserting test data...");

    commandBus.execute(new CreateProductCommand(
        "Adidas terrex-2",
        "Adidas terrex-2 shoes...",
        43.5,
        "adidas-shoes-2.png",
        13
    ));
    commandBus.execute(new CreateProductCommand(
        "Adidas terrex",
        "Some adidas terrex shoes...",
        60.0,
        "adidas-shoes-1.png",
        10
    ));
    commandBus.execute(new CreateProductCommand(
        "Adidas sh",
        "Adidas shoes...",
        51.2,
        "adidas-shoes-3.png",
        2
    ));
    commandBus.execute(new CreateProductCommand(
        "Adidas sport",
        "Adidas shoes new...",
        31.7,
        "adidas-shoes-4.png",
        60
    ));
    commandBus.execute(new CreateProductCommand(
        "Giro bike shoes",
        "Giro shoes...",
        50.2,
        "giro-shoes-1.png",
        35
    ));
    commandBus.execute(new CreateProductCommand(
        "Giro street",
        "Giro street shoes...",
        34.8,
        "giro-shoes-2.png",
        1
    ));
    commandBus.execute(new CreateProductCommand(
        "Giro vibram",
        "Giro vibram shoes...",
        90.0,
        "giro-shoes-3.png",
        10
    ));
    commandBus.execute(new CreateProductCommand(
        "Etnies bike",
        "Etnies bike shoes...",
        46.2,
        "etnies-shoes-1.png",
        10
    ));
    commandBus.execute(new CreateProductCommand(
        "Etnies street",
        "Etnies street shoes...",
        37.9,
        "etnies-shoes-2.png",
        12
    ));
    commandBus.execute(new CreateProductCommand(
        "Endura shirt",
        "Endura shirt...",
        10.9,
        "endura-shirt-1.png",
        40
    ));
    commandBus.execute(new CreateProductCommand(
        "Etnies street shirt",
        "Etnies shirt...",
        12.5,
        "etnies-shirt-1.png",
        15
    ));
    commandBus.execute(new CreateProductCommand(
        "Etnies bike shirt",
        "Etnies bike...",
        13.4,
        "etnies-shirt-2.png",
        23
    ));
    commandBus.execute(new CreateProductCommand(
        "Another Etnies shirt",
        "Etnies street shirt description...",
        14.6,
        "etnies-shirt-3.png",
        4
    ));
    commandBus.execute(new CreateProductCommand(
        "Fox classic",
        "Fox classic shirt description...",
        20.0,
        "fox-shirt-1.png",
        16
    ));
    commandBus.execute(new CreateProductCommand(
        "Fox racing",
        "Fox racing shirt description...",
        35.3,
        "fox-shirt-2.png",
        7
    ));
    commandBus.execute(new CreateProductCommand(
        "Fox street",
        "Fox street shirt description...",
        13.5,
        "fox-shirt-3.png",
        23
    ));
    commandBus.execute(new CreateProductCommand(
        "Fox bike",
        "Fox bike shirt description...",
        19.0,
        "fox-shirt-4.png",
        21
    ));
  }
}
