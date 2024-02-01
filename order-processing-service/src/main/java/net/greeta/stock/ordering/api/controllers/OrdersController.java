package net.greeta.stock.ordering.api.controllers;

import net.greeta.stock.common.domain.dto.order.*;
import net.greeta.stock.ordering.api.application.commands.*;
import net.greeta.stock.ordering.api.application.queries.OrderQueries;
import net.greeta.stock.ordering.api.application.services.IdentityService;
import net.greeta.stock.ordering.api.infrastructure.commandbus.CommandBus;
import net.greeta.stock.ordering.api.infrastructure.exceptions.UnauthorizedException;
import net.greeta.stock.shared.rest.error.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Validated
public class OrdersController {

  private final CommandBus commandBus;
  private final OrderQueries orderQueries;
  private final IdentityService identityService;

  @RequestMapping(value = "cancel", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.OK)
  public void cancelOrder(
    @RequestBody @Valid CancelOrderCommand command,
    @RequestHeader("x-requestid") String requestId
  ) {
    var requestCancelOrder = new CancelOrderIdentifiedCommand(command, UUID.fromString(requestId));
    commandBus.send(requestCancelOrder);
  }

  @RequestMapping(value = "ship", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.OK)
  public void shipOrder(
    @RequestBody @Valid ShipOrderCommand command,
    @RequestHeader("x-requestid") String requestId
  ) {
    var shipOrderCommand = new ShipOrderIdentifiedCommand(command, UUID.fromString(requestId));
    commandBus.send(shipOrderCommand);
  }

  @RequestMapping("{orderId}")
  public ResponseEntity<OrderViewModel.Order> getOrder(@PathVariable String orderId) {
    final var order = orderQueries.getOrder(orderId)
      .orElseThrow(() -> new NotFoundException("Order %s not found".formatted(orderId)));

    if (!order.ownerId().equals(identityService.getUserIdentity()) && !identityService.isAdmin()) {
      throw new UnauthorizedException();
    }

    return ResponseEntity.ok(order);
  }

  @RequestMapping("request-id/{requestId}")
  public ResponseEntity<OrderViewModel.Order> getOrderByRequestId(@PathVariable String requestId) {
    final var order = orderQueries.getOrderByRequestId(requestId)
            .orElseThrow(() -> new NotFoundException("Order with requestId %s not found".formatted(requestId)));

    if (!order.ownerId().equals(identityService.getUserIdentity()) && !identityService.isAdmin()) {
      throw new UnauthorizedException();
    }

    return ResponseEntity.ok(order);
  }

  @RequestMapping()
  public ResponseEntity<List<OrderViewModel.Order>> getAllOrders() {
    return ResponseEntity.ok(orderQueries.allOrders());
  }

  @RequestMapping("user")
  public ResponseEntity<List<OrderViewModel.Order>> getUserOrders() {
    return ResponseEntity.ok(orderQueries.userOrders(identityService.getUserIdentity()));
  }

  @RequestMapping("summaries")
  public ResponseEntity<List<OrderViewModel.OrderSummary>> getUserOrderSummaries() {
    return ResponseEntity.ok(orderQueries.getOrdersFromUser(identityService.getUserIdentity()));
  }

  @RequestMapping(value = "draft", method = RequestMethod.POST)
  public ResponseEntity<OrderDraftDTO> createOrderDraftFromBasketData(
    @RequestBody @Valid CreateOrderDraftCommand command
  ) {
    return ResponseEntity.ok(commandBus.send(command));
  }

}
