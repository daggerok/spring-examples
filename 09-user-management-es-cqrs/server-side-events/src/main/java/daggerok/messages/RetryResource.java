package daggerok.messages;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class RetryResource {

  final MessageService messageService;

  @SneakyThrows
  @GetMapping(produces = TEXT_EVENT_STREAM_VALUE)
  public SseEmitter createSubscription() {
    return messageService.create();
  }

  @SneakyThrows
  @ResponseStatus(CREATED)
  @PostMapping("/broadcast")
  public void sendMessage(@RequestBody final Event event) {
    messageService.send(event);
  }

  @SneakyThrows
  @DeleteMapping("/{subscriptionId}")
  public ResponseEntity closeSubscription(@PathVariable final String subscriptionId) {
    val subscription = messageService.getSubscription(subscriptionId);
    if (isNull(subscription)) return ResponseEntity.notFound().build();
    messageService.closeSubscription(subscriptionId);
    return ResponseEntity.accepted().build();
  }
}
