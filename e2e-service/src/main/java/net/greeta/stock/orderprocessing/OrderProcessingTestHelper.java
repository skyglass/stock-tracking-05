package net.greeta.stock.orderprocessing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.basket.BasketTestHelper;
import net.greeta.stock.catalog.CatalogTestHelper;
import net.greeta.stock.common.domain.dto.basket.BasketCheckout;
import net.greeta.stock.common.domain.dto.basket.BasketItem;
import net.greeta.stock.common.domain.dto.basket.CustomerBasket;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.order.OrderViewModel;
import net.greeta.stock.helper.RetryHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProcessingTestHelper {

    private final CatalogTestHelper catalogTestHelper;

    private final BasketTestHelper basketTestHelper;

    private final OrderProcessingClient orderProcessingClient;


    public OrderViewModel.Order orderViewCheckout(String productName,
                                                  Integer stockQuantity,
                                                  Double productPrice,
                                                  Integer productQuantity,
                                                  String customerId) {
        CatalogItemResponse product = catalogTestHelper
                .createProduct(productName, productPrice, stockQuantity);

        BasketCheckout basketCheckout = basketTestHelper.checkout(product.getProductId(),
                productName, productPrice, productQuantity, customerId);

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



}
