package net.greeta.stock.config;

import net.greeta.stock.config.auth.UserCredentialsProvider;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MockBeanConfig {

    @Bean
    @Primary
    public UserCredentialsProvider userCredentialsProviderSpy(UserCredentialsProvider userCredentialsProvider) {
        return Mockito.spy(UserCredentialsProvider.class);
    }
}
