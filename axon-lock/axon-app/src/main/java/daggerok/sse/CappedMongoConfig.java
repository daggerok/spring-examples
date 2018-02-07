package daggerok.sse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
public class CappedMongoConfig {

  @Bean
  InitializingBean initializingBean(final MongoOperations operations) {
    return () -> {

      final CollectionOptions options = new CollectionOptions(
          Integer.MAX_VALUE, Integer.MAX_VALUE, true
      );

      if (operations.collectionExists(Log.class))
        operations.dropCollection(Log.class);

      operations.createCollection(Log.class, options);
    };
  }
}
