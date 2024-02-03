package net.greeta.stock.catalog.application.integrationevents.events;

import net.greeta.stock.common.domain.dto.catalog.OrderStockItem;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToAwaitingValidationIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private List<OrderStockItem> orderStockItems;
}
