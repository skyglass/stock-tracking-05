package net.greeta.stock.analytics.streamprocessing.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToCancelledIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private String orderStatus;
  private String buyerName;
}
