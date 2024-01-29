package net.greeta.stock.basket;

import net.greeta.stock.common.domain.dto.basket.CustomerBasket;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class BasketRedisConfig {

    @Value("${spring.data.basket.redis.host}")
    private String redisHost;

    @Value("${spring.data.basket.redis.port}")
    private int redisPort;

    @Value("${spring.data.basket.redis.password}")
    private String redisPassword;

    @Bean
    public LettuceConnectionFactory basketRedisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory =
                new LettuceConnectionFactory(redisHost, redisPort);
        lettuceConnectionFactory.setPassword(redisPassword);
        return lettuceConnectionFactory;
    }

    /**
     * Redis template use redis data access
     *
     * @param redisConnectionFactory redisConnectionFactory
     * @return redisTemplate
     */
    @Bean
    public RedisTemplate<String, CustomerBasket> basketRedisTemplate(
            @Qualifier("basketRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, CustomerBasket> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setEnableTransactionSupport(true);
        return template;
    }

}
