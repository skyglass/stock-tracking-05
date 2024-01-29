package net.greeta.stock.analytics.streamprocessing.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStartedIntegrationEvent extends IntegrationEvent {
  private String userId;
}
