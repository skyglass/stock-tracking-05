package net.greeta.stock.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/webjars/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/v3/api-docs/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/favicon.ico").permitAll()

                        .pathMatchers(HttpMethod.GET,"/analytics/v3/api-docs/**").permitAll()
                        .pathMatchers("/analytics", "/analytics/**").permitAll()

                        .pathMatchers(HttpMethod.GET,"/basket/v3/api-docs/**").permitAll()
                        .pathMatchers("/basket", "/basket/**").permitAll()

                        .pathMatchers(HttpMethod.GET,"/basket2/v3/api-docs/**").permitAll()
                        .pathMatchers("/basket2", "/basket2/**").permitAll()

                        .pathMatchers(HttpMethod.GET,"/basket3/v3/api-docs/**").permitAll()
                        .pathMatchers("/basket3", "/basket3/**").permitAll()

                        .pathMatchers(HttpMethod.GET,"/catalog-command/v3/api-docs/**").permitAll()
                        .pathMatchers("/catalog-command", "/catalog-command/**").permitAll()

                        .pathMatchers(HttpMethod.GET,"/catalog-query/v3/api-docs/**").permitAll()
                        .pathMatchers("/catalog-query", "/catalog-query/**").permitAll()

                        .pathMatchers(HttpMethod.GET,"/catalog-query2/v3/api-docs/**").permitAll()
                        .pathMatchers("/catalog-query2", "/catalog-query2/**").permitAll()

                        .pathMatchers(HttpMethod.GET,"/order-processing/v3/api-docs/**").permitAll()
                        .pathMatchers("/order-processing", "/order-processing/**").permitAll()

                        .pathMatchers(HttpMethod.GET,"/order-processing2/v3/api-docs/**").permitAll()
                        .pathMatchers("/order-processing2", "/order-processing2/**").permitAll()

                        .pathMatchers(HttpMethod.GET,"/catalog-command2/v3/api-docs/**").permitAll()
                        .pathMatchers("/catalog-command2", "/catalog-command2/**").permitAll()

                        .pathMatchers(HttpMethod.GET,"/catalog-command3/v3/api-docs/**").permitAll()
                        .pathMatchers("/catalog-command3", "/catalog-command3/**").permitAll()

                        .anyExchange().authenticated()
                        .and()
                        //.exceptionHandling(exceptionHandling -> exceptionHandling
                        //        .authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))
                        .csrf(csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
                        .oauth2ResourceServer().jwt()
                        .jwtAuthenticationConverter(new ReactiveJwtAuthenticationConverterAdapter(jwtAuthConverter))
                )
                .build();
    }

    @Bean
    WebFilter csrfWebFilter() {
        // Required because of https://github.com/spring-projects/spring-security/issues/5766
        return (exchange, chain) -> {
            exchange.getResponse().beforeCommit(() -> Mono.defer(() -> {
                Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
                return csrfToken != null ? csrfToken.then() : Mono.empty();
            }));
            return chain.filter(exchange);
        };
    }

    public static final String STOCK_MANAGER = "STOCK_MANAGER";
    public static final String STOCK_USER = "STOCK_USER";
}
