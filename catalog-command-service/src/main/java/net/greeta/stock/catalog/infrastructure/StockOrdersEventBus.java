package net.greeta.stock.catalog.infrastructure;

import net.greeta.stock.catalog.config.KafkaTopics;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;
import net.greeta.stock.shared.eventhandling.KafkaEventBus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class StockOrdersEventBus extends KafkaEventBus {

  public StockOrdersEventBus(
      KafkaTemplate<String, IntegrationEvent> kafkaTemplate,
      KafkaTopics topics
  ) {
    super(kafkaTemplate, topics.getStockOrders());
  }
}