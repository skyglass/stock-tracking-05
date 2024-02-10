package net.greeta.stock.catalogcommand;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class CatalogCommandJdbcConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.catalog-command")
    public DataSourceProperties catalogCommandDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.catalog-command.hikari")
    public DataSource catalogCommandDataSource() {
        return catalogCommandDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public JdbcTemplate catalogCommandJdbcTemplate(@Qualifier("catalogCommandDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
