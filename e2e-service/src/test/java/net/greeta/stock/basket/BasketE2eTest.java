package net.greeta.stock.basket;

import net.greeta.stock.common.E2eTest;
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
    private BasketTestHelper customerTestHelper;

    //@Autowired
    //private BasketClient customerClient;


    @Test
    void createOrderThenCustomerAccountBalanceReducedTest() {
        System.out.println();
    }


}
