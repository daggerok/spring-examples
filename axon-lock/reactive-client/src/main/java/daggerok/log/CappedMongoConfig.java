package daggerok.log;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;

//@Configuration
public class CappedMongoConfig {

  @Bean
  InitializingBean initializingBean(final MongoOperations operations) {
    return () -> {

      if (operations.collectionExists(Log.class))
        operations.dropCollection(Log.class);

      final CollectionOptions options = CollectionOptions.empty()
                                                         .capped()
                                                         .size(Long.MAX_VALUE)
                                                         .maxDocuments(Long.MAX_VALUE);
      operations.createCollection(Log.class, options);
    };
  }
}
