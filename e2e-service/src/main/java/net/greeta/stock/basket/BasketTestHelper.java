package net.greeta.stock.basket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.basket.builder.BasketCheckoutBuilder;
import net.greeta.stock.basket.builder.BasketItemBuilder;
import net.greeta.stock.basket.builder.CustomerBasketBuilder;
import net.greeta.stock.catalog.CatalogTestHelper;
import net.greeta.stock.common.domain.dto.basket.BasketCheckout;
import net.greeta.stock.common.domain.dto.basket.BasketItem;
import net.greeta.stock.common.domain.dto.basket.CustomerBasket;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class BasketTestHelper {

    private final CatalogTestHelper catalogTestHelper;

    private final BasketClient basketClient;

    private final BasketItemBuilder basketItemBuilder;

    private final BasketCheckoutBuilder basketCheckoutBuilder;

    private final CustomerBasketBuilder customerBasketBuilder;

    @Async
    public CompletableFuture<BasketCheckout> asyncCheckout(UUID productId,
                                                           String productName,
                                                           Integer stockQuantity,
                                                           Double productPrice,
                                                           Integer productQuantity,
                                                           String customerId) {
        log.info("Order checkout with amount {} for product {}", productQuantity, productName);
        return CompletableFuture.completedFuture(checkout(productId, productName,
                productPrice, productQuantity, customerId));
    }

    public BasketCheckout checkout(String productName,
                                   Integer stockQuantity,
                                   Double productPrice,
                                   Integer productQuantity,
                                   String customerId) {
        CatalogItemResponse product = catalogTestHelper
                .createProduct(productName, productPrice, stockQuantity);
        return checkout(product.getProductId(), productName, productPrice, productQuantity, customerId);
    }

    public BasketCheckout checkout(UUID productId,
                                   String productName,
                                   Double productPrice,
                                   Integer productQuantity,
                                   String customerId) {
        BasketItem basketItem = basketItemBuilder.build(
                productId, productName, productPrice, productQuantity);
        CustomerBasket customerBasket = customerBasketBuilder.build(customerId, basketItem);

        BasketCheckout basketCheckout = basketCheckoutBuilder.build(customerId);
        basketClient.directCheckout(customerBasket, basketCheckout.getRequestId());

        return basketCheckout;
    }



}
