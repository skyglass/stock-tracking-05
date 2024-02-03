package net.greeta.stock.ordering.api.application.domaineventhandlers.buyerverified;

import lombok.RequiredArgsConstructor;
import net.greeta.stock.common.domain.dto.order.events.BuyerVerifiedDomainEvent;
import net.greeta.stock.ordering.api.application.domaineventhandlers.DomainEventHandler;
import net.greeta.stock.ordering.domain.aggregatesmodel.order.OrderRepository;
import net.greeta.stock.shared.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

@EventHandler
@RequiredArgsConstructor
public class UpdateOrderWhenBuyerVerifiedDomainEventHandler
    implements DomainEventHandler<BuyerVerifiedDomainEvent> {
  private static final Logger logger = LoggerFactory.getLogger(UpdateOrderWhenBuyerVerifiedDomainEventHandler.class);

  private final OrderRepository orderRepository;

  // Domain Logic comment:
  // When the Buyer has been created or verified that they existed,
  // then we can update the original Order with the BuyerId (foreign keys)
  @EventListener
  public void handle(BuyerVerifiedDomainEvent buyerVerifiedEvent) {
    logger.info(
        "Order with Id: {} has been successfully updated",
        buyerVerifiedEvent.orderId()
    );

    orderRepository.findById(buyerVerifiedEvent.orderId()).ifPresent(order -> {
      order.setBuyerId(buyerVerifiedEvent.buyer().getId());
      orderRepository.save(order);
    });
  }
}
