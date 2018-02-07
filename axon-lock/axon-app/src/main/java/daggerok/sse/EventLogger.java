package daggerok.sse;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventLogger {

  final SseRepository repository;

  @EventHandler
  @Transactional
  public void on(final Object payload) {
    repository.save(Log.of(payload.getClass().getSimpleName(), payload));
  }
}
