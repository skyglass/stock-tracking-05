package net.greeta.stock.basket.integrationevents.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;

import java.util.UUID;

// An Integration Event is an event that can cause side effects to other microsrvices, Bounded-Contexts or external systems.
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductPriceChangedIntegrationEvent extends IntegrationEvent {
  private UUID productId;
  private Double newPrice;
  private Double oldPrice;
}
