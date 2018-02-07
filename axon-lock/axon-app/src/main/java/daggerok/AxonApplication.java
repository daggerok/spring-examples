package daggerok;

import com.mongodb.MongoClient;
import daggerok.guest.Guest;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@SpringBootApplication
public class AxonApplication {

  public static void main(String[] args) {
    SpringApplication.run(AxonApplication.class, args);
  }

  @Configuration
  static class AxonConfig {

    @Bean
    public Serializer serializer() {
      return new JacksonSerializer();
    }

    @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) {

      return new DefaultMongoTemplate(mongoClient, "axonframework")
          .withDomainEventsCollection("domainevents")
          .withSnapshotCollection("snapshotevents");
    }

    @Bean EventStorageEngine eventStorageEngine(final MongoClient mongoClient) {
//      return new MongoEventStorageEngine(serializer(),
//                                         null,
//                                         4,
//                                         new DefaultMongoTemplate(mongoClient),
//                                         new DocumentPerCommitStorageStrategy());
      return new MongoEventStorageEngine(serializer(),
                                         null,
                                         new DefaultMongoTemplate(mongoClient),
                                         new DocumentPerEventStorageStrategy());
    }

    /* snapshots */

    @Bean
    SpringAggregateSnapshotterFactoryBean snapshotterFactoryBean() {
      return new SpringAggregateSnapshotterFactoryBean();
    }

/*
    @Bean("axonEntranceRepository")
    Repository<Entrance> axonEntranceRepository(final EventStore eventStore,
                                                final Snapshotter snapshotter) {

//      return new EventSourcingRepository<>(Entrance.class,
//                                           eventStore,
//                                           new EventCountSnapshotTriggerDefinition(snapshotter, 3));
      return genericRepository(Entrance.class, eventStore, snapshotter, 4);
    }
*/

    //@Bean("axonGuestRepository")
    Repository<Guest> axonGuestRepository(final EventStore eventStore,
                                          final Snapshotter snapshotter) {

      return genericRepository(Guest.class, eventStore, snapshotter, 4);
    }

    private <T> Repository<T> genericRepository(final Class<T> type,
                                                final EventStore eventStore,
                                                final Snapshotter snapshotter,
                                                final int threshold) {
      return new EventSourcingRepository<>(type,
                                           eventStore,
                                           new EventCountSnapshotTriggerDefinition(snapshotter, threshold));
    }
  }
}
