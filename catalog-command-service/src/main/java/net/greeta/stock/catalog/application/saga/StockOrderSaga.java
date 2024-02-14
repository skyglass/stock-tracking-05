package net.greeta.stock.catalog.application.saga;

import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CommandBusRetryHelper;
import net.greeta.stock.catalog.application.commands.confirmstockorder.ConfirmStockOrderCommand;
import net.greeta.stock.catalog.application.commands.confirmstockorderitem.ConfirmStockOrderItemCommand;
import net.greeta.stock.catalog.application.commands.removestock.RemoveStockCommand;
import net.greeta.stock.catalog.application.events.handler.RemoveStockEventHandler;
import net.greeta.stock.catalog.application.events.*;
import net.greeta.stock.catalog.application.integrationevents.IntegrationEventPublisher;
import net.greeta.stock.catalog.application.integrationevents.events.OrderStockConfirmedIntegrationEvent;
import net.greeta.stock.catalog.application.integrationevents.events.StockOrderItem;
import net.greeta.stock.catalog.config.KafkaTopics;
import net.greeta.stock.catalog.application.events.handler.StockOrderEventHandler;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Saga
@Slf4j
public class StockOrderSaga {

	@Autowired
	private transient IntegrationEventPublisher integrationEventService;
	@Autowired
	private transient KafkaTopics kafkaTopics;
	@Autowired
	private transient CommandBusRetryHelper commandBusRetryHelper;
	@Autowired
	private transient StockOrderEventHandler stockOrderEventHandler;
	@Autowired
	private transient RemoveStockEventHandler removeStockEventHandler;

	
	@StartSaga
	@SagaEventHandler(associationProperty="id")
	public void handle(StockOrderCreated event) {
		log.info("StartSaga: StockOrderSaga.StockOrderCreated event started for order {}", event.getId());
		stockOrderEventHandler.on(event);
		if (event.getStockOrderItems().size() > 0) {
			var current = event.getStockOrderItems().get(0);
			RemoveStockCommand command = new RemoveStockCommand(
					current.getProductId(), event.getId(),
					current.getUnits());
			log.info("StockOrderSaga.RemoveStockCommand started for order {} and product {} with quantity {}", event.getId(), current.getProductId(), current.getUnits());
			commandBusRetryHelper.execute(command);
		}
 
	}

	@SagaEventHandler(associationProperty="id")
	public void on(RemoveStockConfirmed event) {
		log.info("StockOrderSaga.RemoveStockConfirmed event started for order {} and product {} with quantity {}",
				event.getId(), event.getProductId(), event.getQuantity());
		ConfirmStockOrderItemCommand command = new ConfirmStockOrderItemCommand(
				event.getId(), event.getProductId(), event.getQuantity());
		commandBusRetryHelper.execute(command);
	}

	@SagaEventHandler(associationProperty="id")
	public void on(RemoveStockRejected event) {
		log.info("StockOrderSaga.RemoveStockRejected event started for order {} and product {} with quantity {}",
				event.getId(), event.getProductId(), event.getQuantity());
		removeStockEventHandler.on(event);
	}
	
	@SagaEventHandler(associationProperty="id")
	public void handle(StockOrderItemConfirmed event) {
		log.info("StockOrderSaga.StockOrderItemConfirmed event started for order {} and product {} with quantity {}",
				event.getId(), event.getProductId(), event.getQuantity());
		StockOrderItem next = event.getNext();
		if (next == null) {
			ConfirmStockOrderCommand command = new ConfirmStockOrderCommand(event.getId());
			log.info("ConfirmStockOrderCommand started for order {}", event.getId());
			commandBusRetryHelper.execute(command);
		} else {
			RemoveStockCommand nextCommand = new RemoveStockCommand(
					next.getProductId(), event.getId(),
					next.getUnits());
			log.info("StockOrderSaga.StockOrderItemConfirmed.RemoveStockCommand started for order {} and product {} with quantity {}",
					event.getId(), nextCommand.getProductId(), nextCommand.getQuantity());
			commandBusRetryHelper.execute(nextCommand);
		}
 
	}

	@EndSaga
	@SagaEventHandler(associationProperty="id")
	@Transactional("kafkaTransactionManager")
	public void handle(StockOrderConfirmed stockOrderConfirmed) {
		log.info("EndSaga: StockOrderSaga.StockOrderConfirmed event started for order {}", stockOrderConfirmed.getId());
		integrationEventService.publish(
				kafkaTopics.getOrderStockConfirmed(),
				new OrderStockConfirmedIntegrationEvent(stockOrderConfirmed.getId().toString()));
		//throw new RuntimeException("Test StockOrderSaga.StockOrderConfirmed Transaction Rollback");
	}

	
}
