package net.greeta.stock.catalog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.catalog.builder.AddStockCommandBuilder;
import net.greeta.stock.catalog.builder.CreateProductCommandBuilder;
import net.greeta.stock.catalogcommand.CatalogCommandClient;
import net.greeta.stock.catalogcommand.CatalogCommandClient2;
import net.greeta.stock.catalogcommand.CatalogCommandClient3;
import net.greeta.stock.catalogquery.CatalogQueryClient;
import net.greeta.stock.catalogquery.CatalogQueryClient2;
import net.greeta.stock.common.GenericTestHelper;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemDto;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.helper.RetryHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogTestHelper extends GenericTestHelper {

    private final CatalogCommandClient catalogCommandClient;

    private final CatalogCommandClient2 catalogCommandClient2;

    private final CatalogCommandClient3 catalogCommandClient3;

    private final CatalogQueryClient catalogQueryClient;

    private final CatalogQueryClient2 catalogQueryClient2;

    private final CreateProductCommandBuilder createProductCommandBuilder;

    private final AddStockCommandBuilder addStockCommandBuilder;

    public CatalogItemResponse createProduct(String name, Double price, Integer availableStock) {
        return catalogCommandClient.createProduct(
                createProductCommandBuilder.build(name, price, availableStock)
        );
    }

    @Async
    public CompletableFuture<CatalogItemResponse> addStockAsync(UUID productId, Integer quantity, int hash) {
        return CompletableFuture.completedFuture(addStock(productId, quantity, hash));
    }

    public CatalogItemResponse addStock(UUID productId, Integer quantity, int hash) {
        if (hash % 3 == 0) {
            return catalogCommandClient.addStock(
                    addStockCommandBuilder.build(productId, quantity));
        }
        if (hash % 3 == 1) {
            return catalogCommandClient2.addStock(
                    addStockCommandBuilder.build(productId, quantity));
        }
        return catalogCommandClient3.addStock(
                addStockCommandBuilder.build(productId, quantity));

    }

    public CatalogItemDto catalogItem(UUID productId, AtomicInteger counter) {
        int hash = counter.getAndIncrement();
        return hash % 2 == 0 ? catalogQueryClient.catalogItem(productId)
                : catalogQueryClient2.catalogItem(productId);

    }



}