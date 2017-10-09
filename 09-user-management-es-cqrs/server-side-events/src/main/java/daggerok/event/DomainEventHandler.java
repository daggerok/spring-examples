package daggerok.event;

import daggerok.messages.Event;
import daggerok.messages.MessageService;
import daggerok.messages.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DomainEventHandler {

  final MessageService messageService;

  @StreamListener(Sink.INPUT)
  public void handle(final GenericMessage message) {
    log.info("received: {}", message);
    messageService.send(new Event().setMessage(message.getPayload().toString())
                                   .setType(Type.MESSAGE));
  }
}
