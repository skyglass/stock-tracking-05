package net.greeta.stock.basket.integrationevents.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.common.domain.dto.basket.CustomerBasket;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;

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
