package net.greeta.stock.catalog.application.saga;

import com.appsdeveloperblog.estore.OrdersService.command.commands.ApproveOrderCommand;
import com.appsdeveloperblog.estore.OrdersService.command.commands.RejectOrderCommand;
import com.appsdeveloperblog.estore.OrdersService.core.events.OrderApprovedEvent;
import com.appsdeveloperblog.estore.OrdersService.core.events.OrderCreatedEvent;
import com.appsdeveloperblog.estore.OrdersService.core.events.OrderRejectedEvent;
import com.appsdeveloperblog.estore.OrdersService.core.model.OrderSummary;
import com.appsdeveloperblog.estore.OrdersService.query.FindOrderQuery;
import com.appsdeveloperblog.estore.core.commands.CancelProductReservationCommand;
import com.appsdeveloperblog.estore.core.commands.ProcessPaymentCommand;
import com.appsdeveloperblog.estore.core.commands.ReserveProductCommand;
import com.appsdeveloperblog.estore.core.events.PaymentProcessedEvent;
import com.appsdeveloperblog.estore.core.events.ProductReservationCancelledEvent;
import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;
import com.appsdeveloperblog.estore.core.model.User;
import com.appsdeveloperblog.estore.core.query.FetchUserPaymentDetailsQuery;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commandbus.CatalogCommandBus;
import net.greeta.stock.catalog.application.integrationevents.IntegrationEventPublisher;
import net.greeta.stock.catalog.application.integrationevents.events.OrderStockConfirmedIntegrationEvent;
import net.greeta.stock.catalog.config.KafkaTopics;
import net.greeta.stock.common.domain.dto.catalog.ConfirmStockOrderItemCommand;
import net.greeta.stock.common.domain.dto.catalog.RemoveStockCommand;
import net.greeta.stock.common.domain.dto.catalog.StockOrderItem;
import net.greeta.stock.common.domain.events.catalog.StockOrderCreated;
import net.greeta.stock.common.domain.events.catalog.StockOrderItemConfirmed;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

		if (event.getStockOrderItems().size() > 0) {
			var current = event.getStockOrderItems().get(0);
			RemoveStockCommand command = new RemoveStockCommand(
					event.getId(), current.getProductId(),
					current.getUnits(), event.getStockOrderItems());
			log.info("RemoveStockCommand started for order {} and product {} with quantity {}", event.getId(), current.getProductId(), current.getUnits());
			commandBus.execute(command);
		}
 
	}
	
	@SagaEventHandler(associationProperty="id")
	public void handle(StockOrderItemConfirmed event) {
		var next = event.getNext();
		if (next == null) {
			integrationEventService.publish(
					kafkaTopics.getOrderStockConfirmed(),
					new OrderStockConfirmedIntegrationEvent(event.getId().toString()));
		} else {
			ConfirmStockOrderItemCommand nextCommand = new ConfirmStockOrderItemCommand(
					event.getId(), next.getProductId(),
					next.getUnits());
			log.info("ConfirmStockOrderItemCommand started for order {} and product {} with quantity {}", nextCommand.orderId(), next.getProductId(), next.getUnits());
			commandBus.execute(nextCommand);
		}
 
	}



	@EndSaga
	@SagaEventHandler(associationProperty="orderId")
	public void handle(OrderApprovedEvent orderApprovedEvent) {
		LOGGER.info("Order is approved. Order Saga is complete for orderId: " + orderApprovedEvent.getOrderId());
	    //SagaLifecycle.end();
		queryUpdateEmitter.emit(FindOrderQuery.class, query -> true, 
				new OrderSummary(orderApprovedEvent.getOrderId(), 
						orderApprovedEvent.getOrderStatus(),
						""));
	}

	@SagaEventHandler(associationProperty="orderId")
	public void handle(ProductReservationCancelledEvent productReservationCancelledEvent) {
		// Create and send a RejectOrderCommand
		RejectOrderCommand rejectOrderCommand = new RejectOrderCommand(productReservationCancelledEvent.getOrderId(),
				productReservationCancelledEvent.getReason());
		
		commandGateway.send(rejectOrderCommand);
	}
	
	@EndSaga
	@SagaEventHandler(associationProperty="orderId")
	public void handle(OrderRejectedEvent orderRejectedEvent) {
		LOGGER.info("Successfully rejected order with id " + orderRejectedEvent.getOrderId());
		
		queryUpdateEmitter.emit(FindOrderQuery.class, query -> true, 
				new OrderSummary(orderRejectedEvent.getOrderId(), 
						orderRejectedEvent.getOrderStatus(),
						orderRejectedEvent.getReason()));
	}
	
	@DeadlineHandler(deadlineName=PAYMENT_PROCESSING_TIMEOUT_DEADLINE)
	public void handlePaymentDeadline(ProductReservedEvent productReservedEvent) {
		LOGGER.info("Payment processing deadline took place. Sending a compensating command to cancel the product reservation");
		cancelProductReservation(productReservedEvent, "Payment timeout");
	}
	
}
