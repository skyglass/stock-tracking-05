package net.greeta.stock.catalogcommand;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CatalogCommandTestDataService {

    @Autowired
    @Qualifier("catalogCommandMongoTemplate")
    private MongoTemplate mongoTemplate;

    public void resetDatabase() {
        mongoTemplate.remove(new Query(), "brand");
        mongoTemplate.remove(new Query(), "category");
    }
}
