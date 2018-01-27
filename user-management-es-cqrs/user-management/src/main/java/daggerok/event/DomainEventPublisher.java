package daggerok.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.Publisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DomainEventPublisher {

  @Publisher(channel = Source.OUTPUT)
  public DomainEvent sendEvent(final DomainEvent event) {
    log.info("sending {}", event);
    return event;
  }
}
