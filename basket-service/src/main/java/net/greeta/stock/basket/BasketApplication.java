package net.greeta.stock.basket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BasketApplication {
  public static void main(String[] args) {
    SpringApplication.run(BasketApplication.class, args);
  }
}
