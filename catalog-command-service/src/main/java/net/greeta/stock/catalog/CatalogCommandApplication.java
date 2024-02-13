package net.greeta.stock.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "net.greeta.stock.catalog" })
@EntityScan(basePackages = { "net.greeta.stock.catalog"})
@EnableTransactionManagement
@EnableRetry
public class CatalogCommandApplication {
  public static void main(String[] args) {
    SpringApplication.run(CatalogCommandApplication.class, args);
  }
}
