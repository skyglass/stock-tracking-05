package net.greeta.stock.orderprocessing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.basket.BasketTestHelper;
import net.greeta.stock.catalog.CatalogTestHelper;
import net.greeta.stock.common.domain.dto.basket.BasketCheckout;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.order.OrderViewModel;
import net.greeta.stock.helper.RetryHelper;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProcessingTestHelper {

    private final CatalogTestHelper catalogTestHelper;

    private final BasketTestHelper basketTestHelper;

    private final OrderProcessingClient orderProcessingClient;

    private final OrderProcessingClient2 orderProcessingClient2;


    public OrderViewModel.Order orderViewCheckout(String productName,
                                                  Integer stockQuantity,
                                                  Double productPrice,
                                                  Integer productQuantity,
                                                  String customerId) {
        CatalogItemResponse product = catalogTestHelper
                .createProduct(productName, productPrice, stockQuantity);

        BasketCheckout basketCheckout = basketTestHelper.checkout(product.getProductId(),
                productName, productPrice, productQuantity, customerId, 0);

        OrderViewModel.Order orderCreated =  RetryHelper.retry(() ->
                orderProcessingClient.getOrderByRequestId(
                        basketCheckout.getRequestId().toString())
        );

        assertNotNull(orderCreated);

        OrderViewModel.OrderItem orderItem = orderCreated.orderItems().get(0);

        assertEquals(basketCheckout.getRequestId().toString(), orderCreated.requestId());
        assertEquals(customerId, orderCreated.ownerId());
        assertEquals(productPrice * productQuantity, orderCreated.total());
        assertEquals(productName, orderItem.productName());
        assertEquals(productPrice, orderItem.unitPrice());

        return orderCreated;
    }

    public OrderViewModel.Order getOrderByRequestId(String requestId, AtomicInteger counter) {
        int hash = counter.getAndIncrement();
        return hash % 2 == 0 ? orderProcessingClient.getOrderByRequestId(requestId)
                : orderProcessingClient2.getOrderByRequestId(requestId);
    }



}
