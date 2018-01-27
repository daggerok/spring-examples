package daggerok.config;

import daggerok.account.Account;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.model.GenericJpaRepository;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static lombok.AccessLevel.PRIVATE;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AxonConfig {

  final PlatformTransactionManager platformTransactionManager;

  @Bean
  public TransactionManager springTransactionManager() {
    return new SpringTransactionManager(platformTransactionManager);
  }

  @Bean // don't wary, aggregator will be stored in persistent db...
  public EventStorageEngine eventStorageEngine() {
    return new InMemoryEventStorageEngine();
  }

  @Bean(name = "accountRepository")
  public Repository<Account> accountRepository(final EventBus eventBus) {
    return new GenericJpaRepository<>(axonEntityManagerProvider(), Account.class, eventBus);
  }

  @Bean
  public EntityManagerProvider axonEntityManagerProvider() {
    return new ContainerManagedEntityManagerProvider();
  }

  @Bean
  public CommandBus asynchronousCommandBus() {
    SimpleCommandBus commandBus = new AsynchronousCommandBus();
    commandBus.registerHandlerInterceptor(new TransactionManagingInterceptor(springTransactionManager()));
    return commandBus;
  }
}
