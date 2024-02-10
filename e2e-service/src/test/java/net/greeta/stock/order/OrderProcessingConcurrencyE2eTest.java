package net.greeta.stock.order;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.basket.BasketTestHelper;
import net.greeta.stock.catalog.CatalogTestHelper;
import net.greeta.stock.catalogquery.CatalogQueryClient;
import net.greeta.stock.common.E2eTest;
import net.greeta.stock.common.domain.dto.basket.BasketCheckout;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemDto;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.order.OrderStatus;
import net.greeta.stock.helper.RetryHelper;
import net.greeta.stock.orderprocessing.OrderProcessingClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(locations = {
        "classpath:application.yml"
})
@Slf4j
public class OrderProcessingConcurrencyE2eTest extends E2eTest {

    @Autowired
    private BasketTestHelper basketTestHelper;

    @Autowired
    private CatalogTestHelper catalogTestHelper;

    @Autowired
    private OrderProcessingClient orderProcessingClient;

    @Autowired
    private CatalogQueryClient catalogQueryClient;


    @Test
    @SneakyThrows
    void createParallelOrdersThenStockIsZeroTest() {
        Integer stockQuantity = 20;
        String productName = "java17";
        Double productPrice = 5.0;
        Integer productQuantity = 2;
        CatalogItemResponse product = catalogTestHelper
                .createProduct(productName, productPrice, stockQuantity);

        // Start the clock
        long start = Instant.now().toEpochMilli();

        int numberOfOrders = 15;
        List<CompletableFuture<BasketCheckout>> createdOrders = new ArrayList<>();
        // Kick of multiple, asynchronous lookups
        for (int i = 0; i < numberOfOrders; i++) {
            CompletableFuture<BasketCheckout> orderSummary = basketTestHelper
                    .asyncCheckout(product.getProductId(), productName, stockQuantity,
                            productPrice, productQuantity, "admin");
            createdOrders.add(orderSummary);
        }

        int numberOfStockUpdates = 5;
        List<CompletableFuture<CatalogItemResponse>> addedStocks = new ArrayList<>();
        // Kick of multiple, asynchronous lookups
        for (int i = 0; i < numberOfStockUpdates; i++) {
            CompletableFuture<CatalogItemResponse> addStockResult = catalogTestHelper.addStockAsync(product.getProductId(), productQuantity);
            addedStocks.add(addStockResult);
        }

        // Wait until they are all done
        CompletableFuture.allOf(createdOrders.toArray(new CompletableFuture[0])).join();
        CompletableFuture.allOf(addedStocks.toArray(new CompletableFuture[0])).join();

        for (CompletableFuture<BasketCheckout> orderFuture: createdOrders) {
            BasketCheckout checkout = orderFuture.get();
            assertNotNull(checkout.getRequestId());
            log.info("--> " + checkout.getRequestId());
            Boolean orderPaid =  RetryHelper.retry(() -> {
                var result = orderProcessingClient.getOrderByRequestId(
                        checkout.getRequestId().toString());
                return Objects.equals(OrderStatus.Paid.getStatus(), result.status());
            });

            assertTrue(orderPaid);
        }

        for (CompletableFuture<CatalogItemResponse> addStockResultFuture: addedStocks) {
            CatalogItemResponse addStockResult = addStockResultFuture.get();
            assertNotNull(addStockResult);
            log.info("Add Stock --> " + addStockResult.getProductId());
        }

        log.info("Elapsed time: " + (Instant.now().toEpochMilli() - start));

        Boolean stockReducedToZero =  RetryHelper.retry(() -> {
            CatalogItemDto catalogItemDto = catalogQueryClient.catalogItem(product.getProductId());
            return catalogItemDto.availableStock() == 0;
        });

        assertTrue(stockReducedToZero);

        //simulate long waiting for stock update
        TimeUnit.MILLISECONDS.sleep(Duration.ofSeconds(3).toMillis());

        //Check that the next order is not approved, because the stock is zero
        BasketCheckout notApprovedCheckout = basketTestHelper.checkout(
                product.getProductId(),
                productName, productPrice, 1, "admin");
        Boolean orderStockNotApproved =  RetryHelper.retry(() -> {
            var result = orderProcessingClient.getOrderByRequestId(
                    notApprovedCheckout.getRequestId().toString());
            return Objects.equals(OrderStatus.AwaitingValidation.getStatus(), result.status());
        });

        assertTrue(orderStockNotApproved);

        //simulate long waiting for stock update
        TimeUnit.MILLISECONDS.sleep(Duration.ofSeconds(3).toMillis());

        catalogTestHelper.addStock(product.getProductId(), 1);

        Boolean orderStockApproved =  RetryHelper.retry(() -> {
            var result = orderProcessingClient.getOrderByRequestId(
                    notApprovedCheckout.getRequestId().toString());
            return Objects.equals(OrderStatus.Paid.getStatus(), result.status());
        });

        assertTrue(orderStockApproved);

        Boolean stockZero =  RetryHelper.retry(() -> {
            CatalogItemDto catalogItemDto = catalogQueryClient.catalogItem(product.getProductId());
            return catalogItemDto.availableStock() == 0;
        });

        assertTrue(stockZero);
    }


}

