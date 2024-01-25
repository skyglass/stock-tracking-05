package net.greeta.stock.ordering.api.application.integrationevents.eventhandling;

import an.awesome.pipelinr.Pipeline;
import net.greeta.stock.ordering.api.application.commands.CancelOrderCommand;
import net.greeta.stock.ordering.api.application.commands.SetPaidOrderStatusCommand;
import net.greeta.stock.ordering.api.application.integrationevents.events.OrderPaymentStatusChangedIntegrationEvent;
import net.greeta.stock.ordering.api.application.integrationevents.events.models.PaymentStatus;
import net.greeta.stock.ordering.shared.EventHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

@EventHandler
@RequiredArgsConstructor
public class PaymentStatusChangedIntegrationEventHandler {
  private static final Logger logger = LoggerFactory.getLogger(PaymentStatusChangedIntegrationEventHandler.class);

  private final Pipeline pipeline;

  @KafkaListener(
      groupId = "${app.kafka.group.paymentStatus}",
      topics = "${spring.kafka.consumer.topic.paymentStatus}"
  )
  public void handle(OrderPaymentStatusChangedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

    if (PaymentStatus.SUCCESS.equals(event.getStatus())) {
      pipeline.send(new SetPaidOrderStatusCommand(event.getOrderId()));
    } else {
      logger.info("The payment of order {} failed. Cancelling the order.", event.getId());
      pipeline.send(new CancelOrderCommand(event.getOrderId()));
    }
  }
}
