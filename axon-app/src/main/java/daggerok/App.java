package daggerok;

import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@ComponentScan(basePackageClasses = { App.class, Jsr310JpaConverters.class, })
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Bean // don't wary, aggregator will be stored in persistent db...
  public EventStorageEngine eventStorageEngine() {
    return new InMemoryEventStorageEngine();
  }
}
