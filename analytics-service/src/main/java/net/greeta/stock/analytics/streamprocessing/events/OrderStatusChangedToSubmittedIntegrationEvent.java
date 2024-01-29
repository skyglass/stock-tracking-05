package net.greeta.stock.analytics.streamprocessing.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.greeta.stock.common.domain.dto.analytics.OrderItem;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderStatusChangedToSubmittedIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private String orderStatus;
  private String buyerName;
  private Double totalPrice;
  private List<OrderItem> orderItems;

}
