package net.greeta.stock.ordering.api.application.domaineventhandlers.buyerandpaymentmethodverified;

import net.greeta.stock.ordering.api.application.domaineventhandlers.DomainEventHandler;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderRepository;
import net.greeta.stock.ordering.domain.events.BuyerAndPaymentMethodVerifiedDomainEvent;
import net.greeta.stock.ordering.shared.EventHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

@EventHandler
@RequiredArgsConstructor
public class UpdateOrderWhenBuyerAndPaymentMethodVerifiedDomainEventHandler
    implements DomainEventHandler<BuyerAndPaymentMethodVerifiedDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(UpdateOrderWhenBuyerAndPaymentMethodVerifiedDomainEventHandler.class);

  private final OrderRepository orderRepository;

  // Domain Logic comment:
  // When the Buyer and Buyer's payment method have been created or verified that they existed,
  // then we can update the original Order with the BuyerId and PaymentId (foreign keys)
  @EventListener
  public void handle(BuyerAndPaymentMethodVerifiedDomainEvent buyerPaymentMethodVerifiedEvent) {
    logger.info(
        "Order with Id: {} has been successfully updated with a payment method {} ({})",
        buyerPaymentMethodVerifiedEvent.orderId(),
        buyerPaymentMethodVerifiedEvent.payment().cardType().getCardName(),
        buyerPaymentMethodVerifiedEvent.payment().getId()
    );

    orderRepository.findById(buyerPaymentMethodVerifiedEvent.orderId()).ifPresent(order -> {
      order.setBuyerId(buyerPaymentMethodVerifiedEvent.buyer().getId());
      order.setPaymentMethodId(buyerPaymentMethodVerifiedEvent.payment().getId());
      orderRepository.save(order);
    });
  }
}
