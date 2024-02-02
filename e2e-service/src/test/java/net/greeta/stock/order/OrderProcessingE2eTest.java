package net.greeta.stock.order;

import net.greeta.stock.basket.BasketTestHelper;
import net.greeta.stock.catalog.CatalogTestHelper;
import net.greeta.stock.catalogquery.CatalogQueryClient;
import net.greeta.stock.common.E2eTest;
import net.greeta.stock.common.domain.dto.basket.BasketCheckout;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemDto;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.common.domain.dto.order.OrderViewModel;
import net.greeta.stock.common.domain.valueobject.OrderStatus;
import net.greeta.stock.config.MockHelper;
import net.greeta.stock.helper.RetryHelper;
import net.greeta.stock.orderprocessing.OrderProcessingClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderProcessingE2eTest extends E2eTest {

    @Autowired
    private BasketTestHelper basketTestHelper;

    @Autowired
    private CatalogTestHelper catalogTestHelper;

    @Autowired
    private OrderProcessingClient orderProcessingClient;

    @Autowired
    private CatalogQueryClient catalogQueryClient;


    @Test
    void test() {
        Integer stockQuantity = 20;
        String productName = "java17";
        Double productPrice = 5.0;
        Integer productQuantity = 2;
        CatalogItemResponse product = catalogTestHelper
                .createProduct(productName, productPrice, stockQuantity);

        BasketCheckout basketCheckout = basketTestHelper.checkout(
                product.getProductId(),
                productName, productPrice, productQuantity, "admin");

        OrderViewModel.Order orderCreated =  RetryHelper.retry(() ->
            orderProcessingClient.getOrderByRequestId(
                    basketCheckout.getRequestId().toString())
        );

        assertNotNull(orderCreated);

        OrderViewModel.OrderItem java17 = orderCreated.orderItems().get(0);

        assertEquals(basketCheckout.getRequestId().toString(), orderCreated.requestId());
        assertEquals("admin", orderCreated.ownerId());
        assertEquals(productPrice * productQuantity, orderCreated.total());
        assertEquals(productName, java17.productName());
        assertEquals(productPrice, java17.unitPrice());

        OrderViewModel.Order orderCreated2 =  RetryHelper.retry(() -> {
            List<OrderViewModel.Order> adminOrders = orderProcessingClient.getUserOrders();
            if (adminOrders.size() == 1 && adminOrders.get(0).orderItems().size() == 1) {
                return adminOrders.get(0);
            }
            return null;
        });

        assertNotNull(orderCreated2);
        OrderViewModel.OrderItem java17_2 = orderCreated2.orderItems().get(0);

        assertEquals(basketCheckout.getRequestId().toString(), orderCreated2.requestId());
        assertEquals("admin", orderCreated2.ownerId());
        assertEquals(productPrice * productQuantity, orderCreated2.total());
        assertEquals(productName, java17_2.productName());
        assertEquals(productPrice, java17_2.unitPrice());

        Boolean stockReduced =  RetryHelper.retry(() -> {
            CatalogItemDto catalogItemDto = catalogQueryClient.catalogItem(product.getProductId());
            return catalogItemDto.availableStock() == stockQuantity - productQuantity;
        });

        assertTrue(stockReduced);
    }


}
