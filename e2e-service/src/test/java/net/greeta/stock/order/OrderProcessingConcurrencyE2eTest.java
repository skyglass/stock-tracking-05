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
import net.greeta.stock.common.domain.dto.order.OrderViewModel;
import net.greeta.stock.helper.RetryHelper;
import net.greeta.stock.orderprocessing.OrderProcessingClient;
import net.greeta.stock.orderprocessing.OrderProcessingTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

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

        int numberOfOrders = 10;
        List<CompletableFuture<BasketCheckout>> createdOrders = new ArrayList<>();
        // Kick of multiple, asynchronous lookups
        for (int i = 0; i < numberOfOrders; i++) {
            CompletableFuture<BasketCheckout> orderSummary = basketTestHelper
                    .asyncCheckout(product.getProductId(), productName, stockQuantity,
                            productPrice, productQuantity, "admin");
            createdOrders.add(orderSummary);
        }

        /*int numberOfDepositUpdates = 5;
        List<CompletableFuture<CreateOrderResponse>> addedDeposits = new ArrayList<>();
        // Kick of multiple, asynchronous lookups
        for (int i = 0; i < numberOfDepositUpdates; i++) {
            CompletableFuture<CreateOrderResponse> addDepositResult = orderTestHelper.createAsyncDeposit(customerId, BigDecimal.valueOf(2.0), i);
            addedDeposits.add(addDepositResult);
        }*/

        // Wait until they are all done
        CompletableFuture.allOf(createdOrders.toArray(new CompletableFuture[0])).join();
        //CompletableFuture.allOf(addedDeposits.toArray(new CompletableFuture[0])).join();

        for (CompletableFuture<BasketCheckout> orderFuture: createdOrders) {
            BasketCheckout checkout = orderFuture.get();
            assertNotNull(checkout.getRequestId());
            log.info("--> " + checkout.getRequestId());
            OrderViewModel.Order orderCreated =  RetryHelper.retry(() ->
                    orderProcessingClient.getOrderByRequestId(
                            checkout.getRequestId().toString())
            );

            assertNotNull(orderCreated);
        }

        /*for (CompletableFuture<CreateOrderResponse> addDepositResultFuture: addedDeposits) {
            CreateOrderResponse addDepositResult = addDepositResultFuture.get();
            assertNotNull(addDepositResult);
            assertEquals(OrderStatus.PENDING, addDepositResult.getOrderStatus());
            log.info("--> " + addDepositResult.getOrderTrackingId());
            Boolean depositPaid =  RetryHelper.retry(() -> {
                var test = orderClient.getOrderByTrackingId(addDepositResult.getOrderTrackingId());
                return test.getOrderStatus() == OrderStatus.PAID;
            });
            assertTrue(depositPaid);
            log.info("Add Deposit --> " + addDepositResult.getOrderTrackingId());
        }*/

        log.info("Elapsed time: " + (Instant.now().toEpochMilli() - start));

        Boolean stockReducedToZero =  RetryHelper.retry(() -> {
            CatalogItemDto catalogItemDto = catalogQueryClient.catalogItem(product.getProductId());
            return catalogItemDto.availableStock() == 0;
        });

        assertTrue(stockReducedToZero);

        //Check that the next order is rejected because the stock is zero
        /*CreateOrderResponse rejectedOrder = orderTestHelper.createOrder(customerId, BigDecimal.valueOf(1.0));
        assertNotNull(rejectedOrder.getOrderTrackingId());
        assertEquals(OrderStatus.PENDING, rejectedOrder.getOrderStatus());
        Boolean orderRejected =  RetryHelper.retry(() -> {
            var test = orderClient.getOrderByTrackingId(rejectedOrder.getOrderTrackingId());
            return test.getOrderStatus() == OrderStatus.CANCELLED;
        });
        assertTrue(orderRejected);*/
    }


}

