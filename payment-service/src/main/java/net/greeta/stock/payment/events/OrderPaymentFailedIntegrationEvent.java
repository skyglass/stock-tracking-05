package net.greeta.stock.payment.events;

import net.greeta.stock.payment.model.PaymentStatus;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderPaymentFailedIntegrationEvent extends IntegrationEvent {
  private String orderId;
  private final PaymentStatus status = PaymentStatus.FAILED;
}
