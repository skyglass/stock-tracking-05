package net.greeta.stock.analytics.streamprocessing;

import net.greeta.stock.analytics.streamprocessing.events.OrderPaymentCompletedIntegrationEvent;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

import static net.greeta.stock.analytics.common.Constants.PAYMENTS_COUNT_STORE;
import static net.greeta.stock.analytics.common.Constants.PAYMENTS_COUNT_STORE_KEY;

@Configuration
public class PaymentStatisticsProcessor {
  @Bean
  public Consumer<
      KStream<String, OrderPaymentCompletedIntegrationEvent>
      > payments() {
    return (paymentCompletedEvents) -> {
      paymentCompletedEvents
          .filter((key, event) -> "successful".equals(event.getStatus()))
          .groupBy((key, value) -> PAYMENTS_COUNT_STORE_KEY)
          .count(Materialized.as(PAYMENTS_COUNT_STORE));
    };
  }
}
