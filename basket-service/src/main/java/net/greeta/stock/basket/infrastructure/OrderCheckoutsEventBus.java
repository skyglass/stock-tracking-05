package net.greeta.stock.basket.infrastructure;

import net.greeta.stock.basket.config.KafkaTopics;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class OrderCheckoutsEventBus extends KafkaEventBus {

  public OrderCheckoutsEventBus(
      KafkaTemplate<String, IntegrationEvent> kafkaTemplate,
      KafkaTopics topics
  ) {
    super(kafkaTemplate, topics.getOrderCheckouts());
  }
}
