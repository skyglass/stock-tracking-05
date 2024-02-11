package net.greeta.stock.catalogcommand;

import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.testdata.JdbcTestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CatalogCommandTestDataService extends JdbcTestDataService {

    @Autowired
    @Qualifier("catalogCommandJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public void resetDatabase() {
        executeString("DELETE FROM stock_order_item");
        executeString("DELETE FROM client_request");
    }

}
