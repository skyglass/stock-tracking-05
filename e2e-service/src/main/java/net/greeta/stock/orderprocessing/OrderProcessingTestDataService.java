package net.greeta.stock.orderprocessing;

import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.testdata.JdbcTestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProcessingTestDataService extends JdbcTestDataService {

    @Autowired
    @Qualifier("orderProcessingJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public void resetDatabase() {
        executeString("DELETE FROM order_item");
        executeString("DELETE FROM client_request");
        executeString("DELETE FROM orders");
        executeString("DELETE FROM outbox");
        executeString("DELETE FROM buyer");
    }
}
