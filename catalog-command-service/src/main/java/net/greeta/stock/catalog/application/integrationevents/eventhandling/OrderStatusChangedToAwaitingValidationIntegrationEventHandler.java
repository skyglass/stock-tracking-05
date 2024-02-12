package net.greeta.stock.catalog.application.integrationevents.eventhandling;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.application.commands.createstockorder.CreateStockOrderIdempotentCommand;
import net.greeta.stock.catalog.application.commands.createstockorder.CreateStockOrderIdentifiedCommand;
import net.greeta.stock.catalog.application.integrationevents.events.*;
import net.greeta.stock.shared.eventhandling.commands.IdempotentCommandBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToAwaitingValidationIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationIntegrationEventHandler.class);

  private final IdempotentCommandBus commandBus;

  @KafkaListener(
          groupId = "${app.kafka.group.ordersWaitingValidation}",
          topics = "${spring.kafka.consumer.topic.ordersWaitingForValidation}"
  )
  public void handle(OrderStatusChangedToAwaitingValidationIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

    CreateStockOrderIdempotentCommand createStockOrderCommand = new CreateStockOrderIdempotentCommand(
            UUID.fromString(event.getOrderId()), event.getOrderStockItems());
    CreateStockOrderIdentifiedCommand createStockOrderIdentifiedCommand = new CreateStockOrderIdentifiedCommand(
            createStockOrderCommand, event.getRequestId());
    commandBus.send(createStockOrderIdentifiedCommand);
  }

}
