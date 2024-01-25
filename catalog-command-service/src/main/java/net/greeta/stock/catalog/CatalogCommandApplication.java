package net.greeta.stock.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class CatalogCommandApplication {
  public static void main(String[] args) {
    SpringApplication.run(CatalogCommandApplication.class, args);
  }
}
