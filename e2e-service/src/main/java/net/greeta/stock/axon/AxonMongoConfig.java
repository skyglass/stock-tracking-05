package net.greeta.stock.axon;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AxonMongoConfig {

    @Bean
    @ConfigurationProperties("spring.data.axon.mongodb")
    public MongoProperties axonMongoProperties() {
        return new MongoProperties();
    }

    @Bean
    public MongoClient axonMongoClient(
            @Qualifier("axonMongoProperties") MongoProperties mongoProperties) {
        return MongoClients.create(getMongoClientSettings(mongoProperties));
    }

    @Bean
    public MongoTemplate axonMongoTemplate(
            @Qualifier("axonMongoClient") MongoClient mongoClient,
            @Qualifier("axonMongoProperties") MongoProperties mongoProperties) {
        return new MongoTemplate(mongoClient, mongoProperties.getDatabase());
    }

    private MongoClientSettings getMongoClientSettings(MongoProperties mongoProperties) {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoProperties.getUri()))
                .build();
    }

}

