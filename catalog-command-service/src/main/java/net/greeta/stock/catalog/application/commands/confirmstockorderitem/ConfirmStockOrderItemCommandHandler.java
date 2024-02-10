package net.greeta.stock.catalog.application.commands.confirmstockorderitem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.events.StockOrderItemConfirmed;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItemRepository;
import net.greeta.stock.catalog.domain.stockorder.StockOrderItemStatus;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Slf4j
public class ConfirmStockOrderItemCommandHandler {

    private final QueryStockOrderItemRepository stockOrderItemRepository;

    @EventHandler
    @Transactional("transactionManager")
    public void on(StockOrderItemConfirmed event) {
        log.info("Handling event: {} ({})", event.getId(), event.getClass().getSimpleName());

        final var queryStockOrderItem = stockOrderItemRepository.findByOrderIdAndProductId(event.getId(), event.getProductId())
                .orElseThrow(() -> new RuntimeException("Stock Order Item not found: orderId = %s, productId = %s".formatted(event.getId(), event.getProductId())));

        queryStockOrderItem.setStockOrderItemStatus(StockOrderItemStatus.StockConfirmed);

        stockOrderItemRepository.save(queryStockOrderItem);

    }

}
