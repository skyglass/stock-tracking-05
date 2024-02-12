package net.greeta.stock.catalog.application.integrationevents.events;

import net.greeta.stock.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
//TODO: move this integration event to shared event-handling module
public class OrderStatusChangedToAwaitingValidationIntegrationEvent extends IntegrationEvent {
  private String orderId;
  //The name must be "orderStockItems" for compatibility with correspondent order-processing service integration event
  private List<StockOrderItem> orderStockItems;
  private UUID requestId;
}
