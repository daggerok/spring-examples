package daggerok.config;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.spring.jdbc.SpringDataSourceConnectionProvider;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static lombok.AccessLevel.PRIVATE;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AxonConfig {

  final DataSource dataSource;
  final PlatformTransactionManager platformTransactionManager;

  @Bean
  public EventStorageEngine eventStorageEngine() {
    return new InMemoryEventStorageEngine();
    //return new JdbcEventStorageEngine(connectionProvider(), transactionManager());
  }

  @Bean
  public CommandBus commandBus() {
    return new AsynchronousCommandBus();
  }

  private TransactionManager transactionManager() {
    return new SpringTransactionManager(platformTransactionManager);
  }

  @Bean
  public ConnectionProvider connectionProvider() {
    return new SpringDataSourceConnectionProvider(dataSource);
  }
}
