package net.greeta.stock.catalog.application.events.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.events.StockOrderCreated;
import net.greeta.stock.catalog.application.integrationevents.events.StockOrderItem;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItem;
import net.greeta.stock.catalog.application.query.model.QueryStockOrderItemRepository;
import net.greeta.stock.catalog.application.query.model.StockOrderItemStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockOrderEventHandler {

    private final QueryStockOrderItemRepository stockOrderItemRepository;

    @Transactional("transactionManager")
    public void on(StockOrderCreated event) {
        log.info("StockOrderItemService: Handling event: {} ({})", event.getId(), event.getClass().getSimpleName());

        for (StockOrderItem stockOrderItem : event.getStockOrderItems()) {
            var queryStockOrderItem = stockOrderItemRepository.findByOrderIdAndProductId(event.getId(), stockOrderItem.getProductId()).orElse(null);
            if (queryStockOrderItem == null) {
                queryStockOrderItem = QueryStockOrderItem.builder()
                        .id(UUID.randomUUID())
                        .orderId(event.getId())
                        .productId(stockOrderItem.getProductId())
                        .quantity(stockOrderItem.getUnits())
                        .stockOrderItemStatus(StockOrderItemStatus.AwaitingConfirmation)
                        .build();

                stockOrderItemRepository.save(queryStockOrderItem);
            }
        }
    }
}
