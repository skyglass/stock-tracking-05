package net.greeta.stock.catalog.application.saga;

import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.integrationevents.IntegrationEventPublisher;
import net.greeta.stock.catalog.application.integrationevents.events.OrderStockConfirmedIntegrationEvent;
import net.greeta.stock.catalog.config.KafkaTopics;
import net.greeta.stock.catalog.domain.catalogitem.commands.RemoveStockCommand;
import net.greeta.stock.shared.eventhandling.events.StockRemoved;
import net.greeta.stock.catalog.domain.stockorder.commands.ConfirmStockOrderCommand;
import net.greeta.stock.catalog.domain.stockorder.commands.ConfirmStockOrderItemCommand;
import net.greeta.stock.catalog.domain.stockorder.events.StockOrderConfirmed;
import net.greeta.stock.catalog.domain.stockorder.events.StockOrderItemConfirmed;
import net.greeta.stock.catalog.application.integrationevents.events.StockOrderItem;
import net.greeta.stock.catalog.application.events.StockOrderCreated;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@Slf4j
public class StockOrderSaga {

	@Autowired
	private transient IntegrationEventPublisher integrationEventService;
	@Autowired
	private transient KafkaTopics kafkaTopics;
	@Autowired
	private transient CatalogCommandBus commandBus;
	
	@StartSaga
	@SagaEventHandler(associationProperty="id")
	public void handle(StockOrderCreated event) {
		log.info("StartSaga: StockOrderSaga.StockOrderCreated event started for order {}", event.getId());
		if (event.getStockOrderItems().size() > 0) {
			var current = event.getStockOrderItems().get(0);
			RemoveStockCommand command = new RemoveStockCommand(
					current.getProductId(), event.getId(),
					current.getUnits());
			log.info("StockOrderSaga.RemoveStockCommand started for order {} and product {} with quantity {}", event.getId(), current.getProductId(), current.getUnits());
			commandBus.execute(command);
		}
 
	}

	@SagaEventHandler(associationProperty="orderId")
	public void on(StockRemoved event) {
		log.info("StockOrderSaga.StockRemoved event started for order {} and product {} with quantity {}",
				event.getOrderId(), event.getId(), event.getQuantity());
		ConfirmStockOrderItemCommand command = new ConfirmStockOrderItemCommand(
				event.getOrderId(), event.getId(), event.getQuantity());
		commandBus.execute(command);
	}
	
	@SagaEventHandler(associationProperty="id")
	public void handle(StockOrderItemConfirmed event) {
		log.info("StockOrderSaga.StockOrderItemConfirmed event started for order {} and product {} with quantity {}",
				event.getId(), event.getProductId(), event.getQuantity());
		StockOrderItem next = event.getNext();
		if (next == null) {
			ConfirmStockOrderCommand command = new ConfirmStockOrderCommand(event.getId());
			log.info("ConfirmStockOrderCommand started for order {}", event.getId());
			commandBus.execute(command);
		} else {
			RemoveStockCommand nextCommand = new RemoveStockCommand(
					next.getProductId(), event.getId(),
					next.getUnits());
			log.info("StockOrderSaga.StockOrderItemConfirmed.RemoveStockCommand started for order {} and product {} with quantity {}",
					event.getId(), nextCommand.getProductId(), nextCommand.getQuantity());
			commandBus.execute(nextCommand);
		}
 
	}

	@EndSaga
	@SagaEventHandler(associationProperty="id")
	public void handle(StockOrderConfirmed stockOrderConfirmed) {
		log.info("EndSaga: StockOrderSaga.StockOrderConfirmed event started for order {}", stockOrderConfirmed.getId());
		integrationEventService.publish(
				kafkaTopics.getOrderStockConfirmed(),
				new OrderStockConfirmedIntegrationEvent(stockOrderConfirmed.getId().toString()));
	}

	
}
