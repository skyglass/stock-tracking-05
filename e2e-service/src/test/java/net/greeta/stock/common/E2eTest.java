package net.greeta.stock.common;

import lombok.SneakyThrows;
import net.greeta.stock.axon.AxonTestDataService;
import net.greeta.stock.basket.BasketTestDataService;
import net.greeta.stock.catalogcommand.CatalogCommandTestDataService;
import net.greeta.stock.catalogquery.CatalogQueryTestDataService;
import net.greeta.stock.client.KafkaClient;
import net.greeta.stock.config.MockHelper;
import net.greeta.stock.config.RedisConfig;
import net.greeta.stock.orderprocessing.OrderProcessingTestDataService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    protected CatalogCommandTestDataService catalogCommandTestDataService;

    @Autowired
    protected CatalogQueryTestDataService catalogQueryTestDataService;

    @Autowired
    private OrderProcessingTestDataService orderProcessingTestDataService;

    @Autowired
    private AxonTestDataService axonTestDataService;

    @Autowired
    private KafkaClient kafkaClient;

    @BeforeEach
    @SneakyThrows
    void cleanup() {
        var result = kafkaClient.clearMessages("Axon.Events");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        mockHelper.mockCredentials(securityOauth2Username, securityOauth2Password);
        basketTestDataService.resetDatabase();
        catalogCommandTestDataService.resetDatabase();
        catalogQueryTestDataService.resetDatabase();
        orderProcessingTestDataService.resetDatabase();
        axonTestDataService.resetDatabase();
        //TimeUnit.MILLISECONDS.sleep(Duration.ofSeconds(1).toMillis());
    }
}
