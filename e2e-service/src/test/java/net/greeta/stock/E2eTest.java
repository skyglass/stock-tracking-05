package net.greeta.stock;

import lombok.SneakyThrows;
import net.greeta.stock.axon.AxonTestDataService;
import net.greeta.stock.basket.BasketTestDataService;
import net.greeta.stock.catalogcommand.CatalogCommandTestDataService;
import net.greeta.stock.catalogquery.CatalogQueryTestDataService;
import net.greeta.stock.orderprocessing.OrderProcessingTestDataService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class E2eTest {

    @Autowired
    private BasketTestDataService basketTestDataService;

    @Autowired
    private CatalogCommandTestDataService catalogCommandTestDataService;

    @Autowired
    private CatalogQueryTestDataService catalogQueryTestDataService;

    @Autowired
    private OrderProcessingTestDataService orderProcessingTestDataService;

    @Autowired
    private AxonTestDataService axonTestDataService;

    @BeforeEach
    @SneakyThrows
    void cleanup() {
        basketTestDataService.resetDatabase();
        catalogCommandTestDataService.resetDatabase();
        catalogQueryTestDataService.resetDatabase();
        orderProcessingTestDataService.resetDatabase();
        axonTestDataService.resetDatabase();
    }
}
