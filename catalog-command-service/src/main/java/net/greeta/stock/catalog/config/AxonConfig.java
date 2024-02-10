package net.greeta.stock.catalog.config;

import com.mongodb.client.MongoClient;
import com.thoughtworks.xstream.XStream;
import jakarta.persistence.EntityManagerFactory;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.extensions.mongo.spring.SpringMongoTransactionManager;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AxonConfig {

  @Bean
  SpringMongoTransactionManager axonTransactionManager(MongoTransactionManager mongoTransactionManager) {
    return new SpringMongoTransactionManager(mongoTransactionManager);
  }

  @Bean
  MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
    return new MongoTransactionManager(dbFactory);
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }



  /**
   * Configures command bus.
   */
  @Bean
  public CommandBus commandBus(TransactionManager axonTransactionManager) {
    SimpleCommandBus commandBus = SimpleCommandBus.builder()
        .transactionManager(axonTransactionManager)
        .build();
    commandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());

    return commandBus;
  }

  /**
   * Configures command gateway.
   */
  @Bean
  public CommandGateway commandGateway(CommandBus commandBus) {
    return DefaultCommandGateway.builder().commandBus(commandBus).build();
  }

  /**
   * Configures Mongo embedded event store.
   */
  @Bean
  public EventStore eventStore(MongoClient client, Serializer serializer, TransactionManager axonTransactionManager) {
    return EmbeddedEventStore.builder()
        .storageEngine(eventStorageEngine(client, serializer, axonTransactionManager)).build();
  }

  /**
   * Configures Mongo based Event Storage Engine.
   */
  private EventStorageEngine eventStorageEngine(MongoClient client, Serializer serializer, TransactionManager axonTransactionManager) {
    return MongoEventStorageEngine.builder()
        .eventSerializer(serializer)
        .snapshotSerializer(serializer)
        .transactionManager(axonTransactionManager)
        .mongoTemplate(axonMongoTemplate(client))
        .build();
  }

  /**
   * Creates Mongo based Token Store.
   */
  @Bean
  public TokenStore tokenStore(MongoClient client, Serializer serializer, TransactionManager axonTransactionManager) {
    return MongoTokenStore.builder()
        .mongoTemplate(axonMongoTemplate(client))
        .transactionManager(axonTransactionManager)
        .serializer(serializer)
        .build();
  }

  @Bean
  public MongoTemplate axonMongoTemplate(MongoClient client) {
    return DefaultMongoTemplate.builder()
            .mongoDatabase(client)
            .build();
  }

  @Bean
  public MongoSagaStore sagaStore(MongoClient client) {
    return MongoSagaStore.builder()
            .serializer(XStreamSerializer.builder().xStream(xStream()).build())
            .mongoTemplate(axonMongoTemplate(client))
            .build();
  }

  // Workaround to avoid the exception "com.thoughtworks.xstream.security.ForbiddenClassException"
  // https://stackoverflow.com/questions/70624317/getting-forbiddenclassexception-in-axon-springboot
  @Bean
  public XStream xStream() {
    XStream xStream = new XStream();
    xStream.allowTypesByWildcard(new String[]{
            "net.greeta.stock.catalog.**",
            "org.hibernate.proxy.pojo.bytebuddy.**"
    });
    return xStream;
  }

  /**
   * Configures a snapshot trigger to create a Snapshot every 5 events.
   * 5 is an arbitrary number used only for testing purposes just to show how the snapshots are stored on Mongo as well.
   */
//  @Bean
//  public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
//    return EventCountSnapshotTriggerDefinition(configuration.snapshotter(), 5);
//  }

}
