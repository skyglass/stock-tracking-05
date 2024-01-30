package net.greeta.stock.config.auth;

import feign.RequestInterceptor;
import net.greeta.stock.config.auth.AccessTokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2FeignConfig {

    @Autowired
    private AccessTokenProvider accessTokenProvider;

    @Value("${spring.cloud.openfeign.client.config.oauth2.url}")
    private String oauth2ClientUrl;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (!StringUtils.startsWith(requestTemplate.url(), oauth2ClientUrl)) {
                requestTemplate.header("Authorization", "Bearer "
                        + accessTokenProvider.getAccessToken());
            }
        };
    }


}
