package net.greeta.stock.config;

import io.lettuce.core.ClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    /**
     * Client Options
     * Reject requests when redis is in disconnected state and
     * Redis will retry to connect automatically when redis server is down
     *
     * @return client options
     */
    @Bean
    public ClientOptions clientOptions() {
        return ClientOptions.builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .autoReconnect(true)
                .build();
    }
}
