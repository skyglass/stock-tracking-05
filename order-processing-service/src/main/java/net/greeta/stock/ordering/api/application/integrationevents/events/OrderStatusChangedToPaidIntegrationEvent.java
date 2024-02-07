package net.greeta.stock.ordering.api.application.integrationevents.events;

import lombok.Setter;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;
import net.greeta.stock.ordering.api.application.integrationevents.events.models.OrderStockItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderStatusChangedToPaidIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private String orderStatus;
  private String buyerName;
  private List<OrderStockItem> orderStockItems;
}
