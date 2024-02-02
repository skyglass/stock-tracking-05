package net.greeta.stock.catalog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.basket.BasketClient;
import net.greeta.stock.catalog.builder.AddStockCommandBuilder;
import net.greeta.stock.catalog.builder.CreateProductCommandBuilder;
import net.greeta.stock.catalogcommand.CatalogCommandClient;
import net.greeta.stock.catalogquery.CatalogQueryClient;
import net.greeta.stock.common.GenericTestHelper;
import net.greeta.stock.common.domain.dto.basket.BasketCheckout;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.catalog.CreateProductCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogTestHelper extends GenericTestHelper {

    private final CatalogCommandClient catalogCommandClient;

    private final CreateProductCommandBuilder createProductCommandBuilder;

    private final AddStockCommandBuilder addStockCommandBuilder;

    public CatalogItemResponse createProduct(String name, Double price, Integer availableStock) {
        return catalogCommandClient.createProduct(
                createProductCommandBuilder.build(name, price, availableStock)
        );
    }

    @Async
    public CompletableFuture<CatalogItemResponse> addStockAsync(UUID productId, Integer quantity) {
        return CompletableFuture.completedFuture(addStock(productId, quantity));
    }

    public CatalogItemResponse addStock(UUID productId, Integer quantity) {
        return catalogCommandClient.addStock(
                addStockCommandBuilder.build(productId, quantity));
    }



}