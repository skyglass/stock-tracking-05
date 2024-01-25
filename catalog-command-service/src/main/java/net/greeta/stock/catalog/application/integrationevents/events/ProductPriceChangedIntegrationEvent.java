package net.greeta.stock.catalog.application.integrationevents.events;

import net.greeta.stock.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductPriceChangedIntegrationEvent extends IntegrationEvent {
  private UUID productId;
  private Double newPrice;
  private Double oldPrice;
}
