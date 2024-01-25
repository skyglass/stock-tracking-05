package net.greeta.stock.ordering;

import net.greeta.stock.shared.outbox.EnableOutbox;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableSpringConfigured
@EnableOutbox
@EnableJpaRepositories
@EntityScan
public class OrderingApplication {
  public static void main(String[] args) {
    SpringApplication.run(OrderingApplication.class, args);
  }
}
