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
import net.greeta.stock.common.domain.valueobject.OrderStatus;
import net.greeta.stock.helper.CalculationHelper;
import net.greeta.stock.helper.RetryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class BasketTestHelper {

    private final CatalogTestHelper catalogTestHelper;

    private final BasketClient basketClient;

    private final BasketItemBuilder basketItemBuilder;

    private final BasketCheckoutBuilder basketCheckoutBuilder;

    private final CustomerBasketBuilder customerBasketBuilder;

    public CustomerBasket checkout(String productName,
                                   Integer stockQuantity,
                                   Double productPrice,
                                   Integer orderQuantity,
                                   String customerId) {
        CatalogItemResponse product = catalogTestHelper
                .createProduct(productName, productPrice, stockQuantity);
        return checkout(product.getProductId(), productName, stockQuantity, productPrice, orderQuantity, customerId);
    }

    public CustomerBasket checkout(UUID productId,
                                   String productName,
                                   Integer stockQuantity,
                                   Double productPrice,
                                   Integer orderQuantity,
                                   String customerId) {
        BasketItem basketItem = basketItemBuilder.build(
                productId, productName, productPrice, orderQuantity);
        CustomerBasket customerBasket = customerBasketBuilder.build(customerId, basketItem);
        customerBasket = basketClient.updateBasket(customerBasket);
        assertNotNull(customerBasket.getId());
        customerBasket = basketClient.getBasketByCustomerId(customerId);
        assertNotNull(customerBasket);
        assertEquals(customerId, customerBasket.getBuyerId());
        assertEquals(1, customerBasket.getItems().size());
        assertEquals(productId, customerBasket.getItems().get(0).getProductId());

        BasketCheckout basketCheckout = basketCheckoutBuilder.build(customerId);
        basketClient.checkout(basketCheckout, basketCheckout.getRequestId().toString());

        return customerBasket;
    }



}
