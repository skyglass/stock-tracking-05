package net.greeta.stock.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import net.greeta.stock.common.domain.dto.order.*;
import net.greeta.stock.common.domain.dto.order.buyer.*;
import net.greeta.stock.ordering.api.application.integrationevents.events.OrderStartedIntegrationEvent;
import net.greeta.stock.ordering.config.KafkaTopics;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.*;
import net.greeta.stock.shared.eventhandling.commands.CommandHandler;
import net.greeta.stock.shared.outbox.IntegrationEventLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@CommandHandler
@RequiredArgsConstructor
public class CreateOrderCommandHandler implements Command.Handler<CreateOrderCommand, Boolean> {
  private static final Logger logger = LoggerFactory.getLogger(CreateOrderCommandHandler.class);

  private final OrderRepository orderRepository;
  private final IntegrationEventLogService integrationEventLogService;
  private final KafkaTopics topics;
  private final Pipeline pipeline;

  @Transactional
  @Override
  public Boolean handle(CreateOrderCommand command) {
    // Add Integration event to clean the basket
    integrationEventLogService.saveEvent(new OrderStartedIntegrationEvent(command.getUserId()), topics.getOrders());

    // Add/Update AggregateRoot
    // DDD patterns comment:
    // Add child entities and value-objects through the Order Aggregate-Root methods and constructor so validations,
    // invariants and business logic make sure that consistency is preserved across the whole aggregate.
    final var order = createOrder(command);
    addProducts(order, toProducts(command.getOrderItems()));

    logger.info("Creating Order");

    orderRepository.save(order);
    pipeline.send(new SetAwaitingValidationOrderStatusCommand(order.getId().getUuid()));

    return true;
  }

  private Order createOrder(CreateOrderCommand command) {
    final var orderData = NewOrderData.builder()
        .userId(UserId.of(command.getUserId()))
        .buyerName(BuyerName.of(command.getUserName()))
        .requestId(command.getRequestId())
        .build();

    return Order.create(orderData);
  }

  private List<Product> toProducts(List<OrderItemDTO> orderItems) {
    return orderItems.stream().map(item -> Product.builder()
        .productId(item.productId())
        .productName(item.productName())
        .pictureUrl(item.pictureUrl())
        .unitPrice(Price.of(item.unitPrice()))
        .discount(Price.of(item.discount()))
        .units(Units.of(item.units()))
        .build())
        .collect(Collectors.toList());
  }

  private void addProducts(Order order, List<Product> products) {
    products.forEach(order::addOrderItem);
  }

}
