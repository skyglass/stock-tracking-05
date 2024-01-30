package net.greeta.stock.common;

import lombok.SneakyThrows;
import net.greeta.stock.axon.AxonTestDataService;
import net.greeta.stock.basket.BasketTestDataService;
import net.greeta.stock.catalogquery.CatalogQueryTestDataService;
import net.greeta.stock.config.MockHelper;
import net.greeta.stock.config.RedisConfig;
import net.greeta.stock.orderprocessing.OrderProcessingTestDataService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class E2eTest {

    @Value("${security.oauth2.username}")
    private String securityOauth2Username;

    @Value("${security.oauth2.password}")
    private String securityOauth2Password;

    @Autowired
    private MockHelper mockHelper;

    @Autowired
    private BasketTestDataService basketTestDataService;

    @Autowired
    private CatalogQueryTestDataService catalogQueryTestDataService;

    @Autowired
    private OrderProcessingTestDataService orderProcessingTestDataService;

    @Autowired
    private AxonTestDataService axonTestDataService;

    @BeforeEach
    @SneakyThrows
    void cleanup() {
        mockHelper.mockCredentials(securityOauth2Username, securityOauth2Password);
        basketTestDataService.resetDatabase();
        catalogQueryTestDataService.resetDatabase();
        orderProcessingTestDataService.resetDatabase();
        axonTestDataService.resetDatabase();
    }
}
