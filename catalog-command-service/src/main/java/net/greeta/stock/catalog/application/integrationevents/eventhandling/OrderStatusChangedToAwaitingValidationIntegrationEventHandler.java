package net.greeta.stock.catalog.application.integrationevents.eventhandling;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.integrationevents.IntegrationEventPublisher;
import net.greeta.stock.catalog.application.integrationevents.events.*;
import net.greeta.stock.catalog.config.KafkaTopics;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItem;
import net.greeta.stock.catalog.domain.catalogitem.CatalogItemRepository;
import net.greeta.stock.common.domain.dto.catalog.ConfirmedOrderStockItem;
import net.greeta.stock.common.domain.dto.catalog.OrderStockItem;
import net.greeta.stock.common.domain.dto.catalog.RemoveStockCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderStatusChangedToAwaitingValidationIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangedToAwaitingValidationIntegrationEventHandler.class);

  private final CatalogCommandBus commandBus;

  @KafkaListener(
          groupId = "${app.kafka.group.ordersWaitingValidation}",
          topics = "${spring.kafka.consumer.topic.ordersWaitingForValidation}"
  )
  @Transactional
  public void handle(OrderStatusChangedToAwaitingValidationIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

    if (event.getOrderStockItems().size() > 0) {
      var current = event.getOrderStockItems().get(0);
      RemoveStockCommand command = new RemoveStockCommand(event.getOrderId(), current.getProductId(),
              current.getUnits(), event.getOrderStockItems());
      logger.info("RemoveStockCommand started for product {} with quantity {}", current.getProductId(), current.getUnits());
      commandBus.execute(command);
    }

  }

}
