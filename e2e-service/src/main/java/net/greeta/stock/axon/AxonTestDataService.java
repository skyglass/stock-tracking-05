package net.greeta.stock.axon;

import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.testdata.JdbcTestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AxonTestDataService {

    @Autowired
    @Qualifier("axonMongoTemplate")
    private MongoTemplate mongoTemplate;


    public void resetDatabase() {
        mongoTemplate.remove(new Query(), "domainevents");
        mongoTemplate.remove(new Query(), "snapshotevents");
        mongoTemplate.remove(new Query(), "trackingtokens");
    }

}
