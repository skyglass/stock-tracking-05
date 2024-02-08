package net.greeta.stock.catalog.domain.catalogitem;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.application.commands.removestock.RemoveStockCommand;
import net.greeta.stock.catalog.application.events.RemoveStockConfirmed;
import net.greeta.stock.catalog.domain.base.AggregateRoot;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.shared.eventhandling.events.*;
import net.greeta.stock.catalog.domain.catalogitem.rules.AvailableStockMustBeEnough;
import net.greeta.stock.catalog.domain.catalogitem.rules.AvailableStockMustNotBeEmpty;
import net.greeta.stock.catalog.domain.catalogitem.rules.PriceMustBeGreaterThanZero;
import net.greeta.stock.catalog.domain.catalogitem.rules.QuantityMustBeGreaterThanZero;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.lang.NonNull;

import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Aggregate
@Slf4j
public class CatalogItemAggregate extends AggregateRoot {
  private ProductName name;
  private String description;
  @Getter
  private Price price;
  private String pictureFileName;
  @Getter
  private Units availableStock;

  public CatalogItemAggregate(
      @NonNull ProductName productName,
      String description,
      @NonNull Price price,
      String pictureFileName,
      @NonNull Units availableStock
  ) {
    super(UUID.randomUUID());
    setName(productName);
    setDescription(description);
    setPrice(price);
    setPictureFileName(pictureFileName);
    setAvailableStock(availableStock);

    apply(new
            CatalogItemCreated(
        id,
        name.getName(),
        description,
        price.getValue(),
        pictureFileName,
        availableStock.getValue()
    ));
  }


  /**
   * If there is sufficient stock of an item, then the integer returned at the end of this call should be the same as
   * quantityDesired.
   * In the event that there isn't sufficient stock available, the method will throw BusinessRuleBrokenException(AvailableStockMustBeEnough).
   * In this case, it is the responsibility of the client to retry again (immediately, or later, after StockAdded Event happens)
   */
  public Units removeStock(RemoveStockCommand command) {
    Units quantity = Units.of(command.getQuantity());
    checkRule(new AvailableStockMustNotBeEmpty(name, availableStock));
    checkRule(new QuantityMustBeGreaterThanZero(quantity));
    checkRule(new AvailableStockMustBeEnough(name, availableStock, quantity));

    final var updatedStock = availableStock.subtract(quantity);

    StockRemoved event = new StockRemoved(command.getProductId(), command.getOrderId(),
            command.getQuantity(), updatedStock.getValue());
    apply(event);
    return updatedStock;
  }

  @EventSourcingHandler
  public void on(StockRemoved event) {
    setAvailableStock(Units.of(event.getAvailableStock()));
    throw new RuntimeException("Test CatalogItemAggregate.StockRemoved");
  }

  /**
   * Increments the quantity of a particular item in inventory.
   *
   * @return the quantity that has been added to stock
   */
  public Units addStock(Units quantity) {
    checkRule(new QuantityMustBeGreaterThanZero(quantity));
    final var updatedStock = availableStock.add(quantity);

    apply(new StockAdded(id, updatedStock.getValue()));

    return updatedStock;
  }

  public void changePrice(@NonNull Price newPrice) {
    requireNonNull(price, "Price cannot be null");
    checkRule(new PriceMustBeGreaterThanZero(newPrice));

    apply(new ProductPriceChanged(id, newPrice.getValue()));
  }

  public void changeName(@NonNull ProductName name) {
    requireNonNull(price, "Product name cannot be null");

    apply(new ProductNameChanged(id, name.getName()));
  }

  @SuppressWarnings("unused")
  @EventSourcingHandler
  private void on(CatalogItemCreated event) {
    this.id = event.getId();
    setName(ProductName.of(event.getName()));
    setDescription(event.getDescription());
    setPrice(Price.of(event.getPrice()));
    setPictureFileName(event.getPictureFileName());
    setAvailableStock(Units.of(event.getAvailableStock()));
  }

  @SuppressWarnings("unused")
  @EventSourcingHandler
  private void on(StockAdded event) {
    setAvailableStock(Units.of(event.getAvailableStock()));
  }

  @SuppressWarnings("unused")
  @EventSourcingHandler
  private void on(ProductNameChanged event) {
    setName(ProductName.of(event.getName()));
  }

  @SuppressWarnings("unused")
  @EventSourcingHandler
  private void on(ProductPriceChanged event) {
    setPrice(Price.of(event.getPrice()));
  }

  private void setName(ProductName name) {
    requireNonNull(name, "Product name cannot be null");
    this.name = name;
  }

  private void setDescription(String description) {
    this.description = description;
  }

  private void setPrice(Price price) {
    this.price = price;
  }

  private void setPictureFileName(String pictureFileName) {
    this.pictureFileName = pictureFileName;
  }

  private void setAvailableStock(Units availableStock) {
    requireNonNull(availableStock, "Available stock cannot be null");
    this.availableStock = availableStock;
  }

}
