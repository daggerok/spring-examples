package daggerok.log;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LogRepository extends ReactiveMongoRepository<Log, String> {

  @Tailable
  Flux<Log> findBy();

  @Override Mono<Log> findById(@Param("id") String id);
}
