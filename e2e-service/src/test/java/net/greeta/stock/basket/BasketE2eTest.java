package net.greeta.stock.basket;

import net.greeta.stock.catalog.CatalogTestHelper;
import net.greeta.stock.common.E2eTest;
import net.greeta.stock.common.domain.dto.catalog.CatalogItemResponse;
import net.greeta.stock.config.MockHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {
        "classpath:application.yml"
})
public class BasketE2eTest extends E2eTest {

    @Autowired
    private BasketTestHelper basketTestHelper;

    @Autowired
    private CatalogTestHelper catalogTestHelper;

    @Autowired
    private MockHelper mockHelper;


    @Test
    void test() {
        basketTestHelper.checkout(
                "java17", 20,
                5.0, 2, "admin");

        CatalogItemResponse product = catalogTestHelper
                .createProduct("java21", 5.0, 20);

        mockHelper.mockCredentials("user", "user");

        basketTestHelper.checkout(product.getProductId(),
                "java21", 5.0, 2, "user");
    }


}
