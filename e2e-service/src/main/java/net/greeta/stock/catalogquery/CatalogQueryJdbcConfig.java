package net.greeta.stock.catalogquery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class CatalogQueryJdbcConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.catalog-query")
    public DataSourceProperties catalogQueryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.catalog-query.hikari")
    public DataSource catalogQueryDataSource() {
        return catalogQueryDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public JdbcTemplate catalogQueryJdbcTemplate(@Qualifier("catalogQueryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
