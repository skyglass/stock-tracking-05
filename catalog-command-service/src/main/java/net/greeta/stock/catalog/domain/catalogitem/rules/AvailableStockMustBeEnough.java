package net.greeta.stock.catalog.domain.catalogitem.rules;

import net.greeta.stock.catalog.domain.base.BusinessRule;
import net.greeta.stock.catalog.domain.catalogitem.ProductName;
import net.greeta.stock.catalog.domain.catalogitem.Units;

public record AvailableStockMustBeEnough(
        ProductName name,
        Units availableStock,
        Units requestedStock
) implements BusinessRule {

    @Override
    public boolean broken() {
        return requestedStock.greaterThan(availableStock);
    }

    @Override
    public String message() {
        return "Not enough stock, available stock %s is less than requested stock %s".formatted(availableStock, requestedStock);
    }

}


