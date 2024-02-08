package net.greeta.stock.catalog.domain.stockorder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.events.RemoveStockConfirmed;
import net.greeta.stock.catalog.application.events.StockOrderCreated;
import net.greeta.stock.catalog.application.integrationevents.events.ConfirmedStockOrderItem;
import net.greeta.stock.catalog.application.integrationevents.events.StockOrderItem;
import net.greeta.stock.catalog.application.models.StockOrderResponse;
import net.greeta.stock.catalog.domain.base.AggregateRoot;
import net.greeta.stock.catalog.application.commands.removestock.RemoveStockCommand;
import net.greeta.stock.catalog.application.commands.confirmstockorder.ConfirmStockOrderCommand;
import net.greeta.stock.catalog.application.commands.confirmstockorderitem.ConfirmStockOrderItemCommand;
import net.greeta.stock.catalog.application.events.StockOrderConfirmed;
import net.greeta.stock.catalog.application.events.StockOrderItemConfirmed;
import net.greeta.stock.catalog.domain.catalogitem.Units;
import net.greeta.stock.catalog.domain.catalogitem.rules.AvailableStockMustBeEnough;
import net.greeta.stock.catalog.domain.catalogitem.rules.AvailableStockMustNotBeEmpty;
import net.greeta.stock.catalog.domain.catalogitem.rules.QuantityMustBeGreaterThanZero;
import net.greeta.stock.shared.eventhandling.events.StockRemoved;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

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

    private boolean allConfirmed = false;

    public StockOrderAggregate(UUID orderId, List<StockOrderItem> stockOrderItems) {
        super(orderId);
        StockOrderCreated stockOrderCreated = new StockOrderCreated(orderId, stockOrderItems);
        apply(stockOrderCreated);
    }

    @EventSourcingHandler
    public void on(StockOrderCreated event) {
        this.id = event.getId();
        this.stockOrderItems = event.getStockOrderItems();
    }

    public void removeStockConfirmed(StockRemoved event) {
        RemoveStockConfirmed confirmEvent = new RemoveStockConfirmed(
                event.getOrderId(), event.getId(),
                event.getQuantity(), event.getAvailableStock());
        apply(confirmEvent);
    }

    @EventSourcingHandler
    public void on(RemoveStockConfirmed event) {
        log.info("StockOrderAggregate.RemoveStockConfirmed event handler started for order {} and product {} with quantity {}",
                event.getId(), event.getProductId(), event.getQuantity());
    }

    @CommandHandler
    public StockOrderResponse handle(ConfirmStockOrderItemCommand command) {
        log.info("StockOrderAggregate.ConfirmStockOrderItemCommand started for order {} and product {} with quantity {}", command.getOrderId(), command.getProductId(), command.getQuantity());
        apply(new StockOrderItemConfirmed(id, command.getProductId(),
                command.getQuantity(), getNext(command.getProductId()), true));

        return StockOrderResponse.builder()
                .orderId(id)
                .version(version)
                .build();
    }

    @EventSourcingHandler
    public void on(StockOrderItemConfirmed event) {
        if (isAlreadyHandled(event.getProductId())) {
            log.info("StockOrderAggregate.ConfirmStockOrderItemCommand for order {} has already been handled for product {} with quantity {}",
                    id, event.getProductId(),
                    event.getQuantity());
        } else {
            confirmedStockOrderItems.add(
                    createConfirmedStockOrderItem(
                            event.getProductId(), event.getQuantity()));
        }
    }

    @CommandHandler
    public StockOrderResponse handle(ConfirmStockOrderCommand command) {
        log.info("StockOrderAggregate.ConfirmStockOrderCommand started for order {}", command.getOrderId());
        apply(new StockOrderConfirmed(id));

        return StockOrderResponse.builder()
                .orderId(id)
                .version(version)
                .build();
    }

    @EventSourcingHandler
    public void on(StockOrderConfirmed event) {
        this.allConfirmed = true;
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
        throw new RuntimeException("StockOrderAggregate.StockOrderItemConfirmed contains wrong productId: " + productId + ". Please, fix the bug!");
    }

    private boolean isAlreadyHandled(UUID productId) {
        return allConfirmed || confirmedStockOrderItems
                .stream().map(ConfirmedStockOrderItem::getProductId)
                .anyMatch(pi -> Objects.equals(productId, pi));
    }
}
