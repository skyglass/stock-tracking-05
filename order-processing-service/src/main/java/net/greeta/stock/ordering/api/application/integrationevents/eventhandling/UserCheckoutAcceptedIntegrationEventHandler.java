package net.greeta.stock.ordering.api.application.integrationevents.eventhandling;

import net.greeta.stock.ordering.api.application.commands.CreateOrderCommand;
import net.greeta.stock.ordering.api.application.commands.CreateOrderIdentifiedCommand;
import net.greeta.stock.ordering.api.application.integrationevents.events.UserCheckoutAcceptedIntegrationEvent;
import net.greeta.stock.ordering.api.infrastructure.commandbus.CommandBus;
import net.greeta.stock.shared.eventhandling.EventHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

@EventHandler
@RequiredArgsConstructor
public class UserCheckoutAcceptedIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(UserCheckoutAcceptedIntegrationEventHandler.class);

  private final CommandBus commandBus;

  /**
   * Integration event handler which starts the create order process.
   *
   * @param event Integration event message which is sent by the  basket.api once it has successfully process the
   *              order items.
   */
  @KafkaListener(
      groupId = "${app.kafka.group.orderCheckouts}",
      topics = "${spring.kafka.consumer.topic.orderCheckouts}"
  )
  public void handle(UserCheckoutAcceptedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

    if (event.getRequestId() != null) {
      createOrder(event);
    } else {
      logger.info("Invalid IntegrationEvent - RequestId is missing - {}", event.getClass().getSimpleName());
    }
  }

  private void createOrder(UserCheckoutAcceptedIntegrationEvent event) {
    var createOrderCommand = CreateOrderCommand.builder()
        .basketItems(event.getBasket().getItems())
        .userId(event.getUserId())
        .userName(event.getUserName())
        .requestId(event.getRequestId())
        .build();
    var requestCreateOrder = new CreateOrderIdentifiedCommand(createOrderCommand, event.getRequestId());
    var result = commandBus.send(requestCreateOrder);

    if (result) {
      logger.info("CreateOrderCommand succeeded - RequestId: {}", event.getRequestId());
    } else {
      logger.info("CreateOrderCommand failed - RequestId: {}", event.getRequestId());
    }
  }

}
