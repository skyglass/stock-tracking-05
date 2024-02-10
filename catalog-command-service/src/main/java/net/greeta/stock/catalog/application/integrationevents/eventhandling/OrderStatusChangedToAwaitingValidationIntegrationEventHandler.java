package net.greeta.stock.catalog.application.integrationevents.eventhandling;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.commands.createstockorder.CreateStockOrderCommand;
import net.greeta.stock.catalog.application.integrationevents.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToAwaitingValidationIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationIntegrationEventHandler.class);

  private final CatalogCommandBus commandBus;

  @KafkaListener(
          groupId = "${app.kafka.group.ordersWaitingValidation}",
          topics = "${spring.kafka.consumer.topic.ordersWaitingForValidation}"
  )
  @Transactional("mongoTransactionManager")
  public void handle(OrderStatusChangedToAwaitingValidationIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

    CreateStockOrderCommand createStockOrderCommand = new CreateStockOrderCommand(
            UUID.fromString(event.getOrderId()), event.getOrderStockItems());
    commandBus.execute(createStockOrderCommand);
  }

}
