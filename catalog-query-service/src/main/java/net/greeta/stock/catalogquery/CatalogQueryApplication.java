package net.greeta.stock.catalogquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "net.greeta.stock.catalogquery.model" })
@EntityScan(basePackages = { "net.greeta.stock.catalogquery.model"})
@SpringBootApplication(scanBasePackages = { "net.greeta.stock.catalogquery" })
public class CatalogQueryApplication {
  public static void main(String[] args) {
    SpringApplication.run(CatalogQueryApplication.class, args);
  }
}
