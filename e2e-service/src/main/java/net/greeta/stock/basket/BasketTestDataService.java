package net.greeta.stock.basket;

import lombok.extern.slf4j.Slf4j;
import net.greeta.stock.common.domain.dto.basket.CustomerBasket;
import net.greeta.stock.testdata.JdbcTestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BasketTestDataService {

    @Autowired
    @Qualifier("basketRedisTemplate")
    private RedisTemplate<String, CustomerBasket> basketRedisTemplate;

    public void resetDatabase() {
        basketRedisTemplate.getConnectionFactory().getConnection().flushAll();
    }

}
