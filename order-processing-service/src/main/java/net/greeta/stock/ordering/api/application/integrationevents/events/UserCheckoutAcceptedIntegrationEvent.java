package net.greeta.stock.ordering.api.application.integrationevents.events;

import net.greeta.stock.shared.eventhandling.IntegrationEvent;
import net.greeta.stock.ordering.api.application.models.CustomerBasket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCheckoutAcceptedIntegrationEvent extends IntegrationEvent {
  private String userId;
  private String userName;
  private String buyer;
  private UUID requestId;
  private CustomerBasket basket;
}
