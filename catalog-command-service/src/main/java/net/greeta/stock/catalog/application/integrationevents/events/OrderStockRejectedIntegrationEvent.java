package net.greeta.stock.catalog.application.integrationevents.events;

import net.greeta.stock.common.domain.dto.catalog.ConfirmedOrderStockItem;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderStockRejectedIntegrationEvent extends IntegrationEvent {
  private String orderId;
}
