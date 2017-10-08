package daggerok.config;

import daggerok.AxonComplainApplication;
import daggerok.core.Complaint;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
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
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static lombok.AccessLevel.PRIVATE;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@ComponentScan(basePackageClasses = AxonComplainApplication.class)
public class AxonConfig {

  @PersistenceContext
  final EntityManager entityManager;
  final PlatformTransactionManager platformTransactionManager;

  @Bean
  public EntityManagerProvider entityManagerProvider() {
    val entityManagerProvider = new ContainerManagedEntityManagerProvider();
    entityManagerProvider.setEntityManager(entityManager);
    return entityManagerProvider;
  }

  @Bean
  public TransactionManager springTransactionManager() {
    return new SpringTransactionManager(platformTransactionManager);
  }
/*
  @Bean
  public JpaEventStorageEngine eventStorageEngine() {
    val res = new JpaEventStorageEngine(entityManagerProvider(), springTransactionManager());
    res.appendEvents();
    return res;
  }
*/
  @Bean // don't wary, aggregator will be stored in persistent db...
  public EventStorageEngine eventStorageEngine() {
    return new InMemoryEventStorageEngine();
  }

  @Bean(name = "complaintRepository")
  public Repository<Complaint> complaintRepository(final EventBus eventBus) {
    return new GenericJpaRepository<>(entityManagerProvider(), Complaint.class, eventBus);
  }

  @Bean
  public CommandBus asynchronousCommandBus() {
    SimpleCommandBus commandBus = new AsynchronousCommandBus();
    commandBus.registerHandlerInterceptor(new TransactionManagingInterceptor(springTransactionManager()));
    return commandBus;
  }
}
