package net.greeta.stock.orderprocessing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class OrderProcessingJdbcConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.order-processing")
    public DataSourceProperties orderProcessingDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.order-processing.hikari")
    public DataSource orderProcessingDataSource() {
        return orderProcessingDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public JdbcTemplate orderProcessingJdbcTemplate(@Qualifier("orderProcessingDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}