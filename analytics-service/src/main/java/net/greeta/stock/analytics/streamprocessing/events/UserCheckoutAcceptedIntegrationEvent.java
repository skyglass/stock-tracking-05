package net.greeta.stock.analytics.streamprocessing.events;

import net.greeta.stock.common.domain.dto.analytics.CustomerBasket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCheckoutAcceptedIntegrationEvent extends IntegrationEvent {
  private String userId;
  private CustomerBasket basket;
}
