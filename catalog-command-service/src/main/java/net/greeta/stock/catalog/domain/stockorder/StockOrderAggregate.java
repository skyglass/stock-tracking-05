package net.greeta.stock.catalog.domain.stockorder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.integrationevents.events.OrderStockConfirmedIntegrationEvent;
import net.greeta.stock.catalog.domain.base.AggregateRoot;
import net.greeta.stock.catalog.domain.catalogitem.Units;
import net.greeta.stock.common.domain.dto.catalog.*;
import net.greeta.stock.common.domain.events.catalog.StockOrderCreated;
import net.greeta.stock.common.domain.events.catalog.StockOrderItemConfirmed;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Aggregate
@Slf4j
public class StockOrderAggregate extends AggregateRoot {

    private List<ConfirmedStockOrderItem> confirmedStockOrderItems = new ArrayList<>();

    private List<StockOrderItem> stockOrderItems;

    @CommandHandler
    public StockOrderAggregate(CreateStockOrderCommand createStockOrderCommand) {
        StockOrderCreated stockOrderCreated = new StockOrderCreated(
                createStockOrderCommand.orderId(),
                createStockOrderCommand.stockOrderItems());
        apply(stockOrderCreated);
    }

    @EventSourcingHandler
    public void on(StockOrderCreated event) {
        this.id = event.getId();
        this.stockOrderItems = event.getStockOrderItems();
    }

    @CommandHandler
    public void handle(ConfirmStockOrderItemCommand command) {
        log.info("ConfirmStockOrderItemCommand started for order {} and product {} with quantity {}", command.orderId(), command.productId(), command.quantity());
        apply(new StockOrderItemConfirmed(id, command.productId(),
                command.quantity(), getNext(command.productId()), true));
    }

    @EventSourcingHandler
    public void on(StockOrderItemConfirmed event) {
        if (isAlreadyHandled(event.getProductId())) {
            log.info("ConfirmStockOrderItemCommand for order {} has already been handled for product {} with quantity {}",
                    id, event.getProductId(),
                    event.getQuantity());
            return;
        }
        confirmedStockOrderItems.add(
                createConfirmedStockOrderItem(
                        event.getProductId(), event.getQuantity()));
    }

    private ConfirmedStockOrderItem createConfirmedStockOrderItem(UUID productId, Integer quantity) {
        return new ConfirmedStockOrderItem(productId, quantity, true);
    }

    private StockOrderItem getNext(UUID productId) {
        for (int i = 0; i < stockOrderItems.size(); i++) {
            var item = stockOrderItems.get(i);
            if (Objects.equals(productId, item.getProductId())) {
                return i < stockOrderItems.size() - 1 ? stockOrderItems.get(i + 1) : null;
            }
        }
        //should never happen!
        throw new RuntimeException("StockOrderItemConfirmed contains wrong productId: " + productId + ". Please, fix the bug!");
    }

    private boolean isAlreadyHandled(UUID productId) {
        return confirmedStockOrderItems
                .stream().map(ConfirmedStockOrderItem::getProductId)
                .anyMatch(pi -> Objects.equals(productId, pi));
    }
}
