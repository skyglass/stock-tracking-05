package net.greeta.stock.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"net.greeta.stock.catalog", "net.greeta.stock.shared"})
@ComponentScan(basePackages = {"net.greeta.stock.catalog", "net.greeta.stock.shared"})
public class CatalogCommandApplication {
  public static void main(String[] args) {
    SpringApplication.run(CatalogCommandApplication.class, args);
  }
}
