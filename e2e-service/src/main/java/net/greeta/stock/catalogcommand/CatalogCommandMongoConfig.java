package net.greeta.stock.catalogcommand;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class CatalogCommandMongoConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.data.catalog-command.mongodb")
    public MongoProperties catalogCommandMongoProperties() {
        return new MongoProperties();
    }

    @Bean
    public MongoClient catalogCommandMongoClient(
            @Qualifier("catalogCommandMongoProperties") MongoProperties mongoProperties) {
        return MongoClients.create(getMongoClientSettings(mongoProperties));
    }

    @Bean
    public MongoTemplate catalogCommandMongoTemplate(
            @Qualifier("catalogCommandMongoClient") MongoClient mongoClient,
            @Qualifier("catalogCommandMongoProperties") MongoProperties mongoProperties) {
        return new MongoTemplate(mongoClient, mongoProperties.getDatabase());
    }

    private MongoClientSettings getMongoClientSettings(MongoProperties mongoProperties) {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoProperties.getUri()))
                .build();
    }

}

