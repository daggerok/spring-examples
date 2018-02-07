package daggerok.sse;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SseRepository extends MongoRepository<Log, String> {}
