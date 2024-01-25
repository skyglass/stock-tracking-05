package net.greeta.stock.ordering.api.application.integrationevents.events;

import net.greeta.stock.ordering.api.application.integrationevents.events.models.PaymentStatus;
import net.greeta.stock.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderPaymentStatusChangedIntegrationEvent extends IntegrationEvent {
    private String orderId;
    private PaymentStatus status;
}
