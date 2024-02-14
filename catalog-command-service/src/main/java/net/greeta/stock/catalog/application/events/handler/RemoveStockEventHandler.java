package net.greeta.stock.catalog.application.events.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.events.RemoveStockRejected;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItemRepository;
import net.greeta.stock.catalog.application.query.model.StockOrderItemStatus;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Slf4j
public class RemoveStockEventHandler {
  private final QueryStockOrderItemRepository stockOrderItemRepository;

  @Transactional("transactionManager")
  public void on(RemoveStockRejected event) {
    log.info("Handling event: {} ({})", event.getId(), event.getClass().getSimpleName());
    log.info("RemoveStockEventHandler.RemoveStockRejected event handler started for order {} and product {} with available stock {}",
            event.getId(), event.getProductId(), event.getAvailableStock());

    final var queryStockOrderItem = stockOrderItemRepository.findByOrderIdAndProductId(event.getId(), event.getProductId())
            .orElseThrow(() -> new RuntimeException("Stock Order Item not found: orderId = %s, productId = %s".formatted(event.getId(), event.getProductId())));

    queryStockOrderItem.setStockOrderItemStatus(StockOrderItemStatus.StockRejected);

    stockOrderItemRepository.save(queryStockOrderItem);

  }
}

