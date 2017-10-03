package daggerok.messages;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

  public static final List<String> ignores = asList(
      "ResponseBodyEmitter is already set complete",
      "Broken pipe"
  );
  static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-s-SSS");
  @Qualifier("sseEmitterRepository")
  final Map<String, SseEmitter> sseEmitterRepository;

  static Event connectMessage(final String subscriptionId) {
    return new Event().setId(subscriptionId)
                      .setType(Type.CONNECT);
  }

  static Event disconnectMessage(final String subscriptionId) {
    return new Event().setId(subscriptionId)
                      .setType(Type.DISCONNECT);
  }

  static Event timeoutMessage(final String subscriptionId) {
    return new Event().setId(subscriptionId)
                      .setType(Type.TIMEOUT);
  }

  public SseEmitter create() {
    val sseEmitter = new SseEmitter(Long.MAX_VALUE);
    val subscriptionId = ZonedDateTime.now().format(FORMAT);
    sseEmitterRepository.put(subscriptionId, sseEmitter);
    sseEmitter.onTimeout(() -> onTimeout(subscriptionId));
    sseEmitter.onCompletion(() -> onCompletion(subscriptionId));
    send(subscriptionId, sseEmitter, connectMessage(subscriptionId));
    return sseEmitter;
  }

  public SseEmitter getSubscription(final String subscriptionId) {
    return sseEmitterRepository.get(subscriptionId);
  }

  public void closeSubscription(final String subscriptionId) {
    ofNullable(sseEmitterRepository.get(subscriptionId)).ifPresent(sseEmitter -> {
      sseEmitter.onCompletion(() -> log.warn("subscription {} is closed.", subscriptionId));
      sseEmitter.complete();
      notify(disconnectMessage(subscriptionId));
    });
  }

  @SneakyThrows
  public void send(final Serializable payload) {
    sseEmitterRepository.forEach((id, sseEmitter) -> send(id, sseEmitter, payload));
  }

  @Async
  public void send(@NotNull final String subscriptionId,
                   @NotNull final SseEmitter sseEmitter,
                   @NotNull final Serializable payload) {

    Try.run(() -> sseEmitter.send(payload)).onFailure(e -> {
      val err = e.getLocalizedMessage();
      if (ignores.stream().filter(err::contains).count() > 0)
        log.warn("emitter abort: '{}'", err);
      else
        log.error("emitting failed: '{}'", err, e);
      sseEmitterRepository.remove(subscriptionId);
    });
  }

  private void onCompletion(final String subscriptionId) {
    log.info("{} completed.", subscriptionId);
    remove(subscriptionId);
    notify(disconnectMessage(subscriptionId));
  }

  private void onTimeout(final String subscriptionId) {
    log.warn("removing {} by timeout", subscriptionId);
    remove(subscriptionId);
    notify(timeoutMessage(subscriptionId));
  }

  private void remove(final String subscriptionId) {
    ofNullable(sseEmitterRepository.remove(subscriptionId))
        .ifPresent(ResponseBodyEmitter::complete);
  }

  private void notify(final Serializable payload) {
    sseEmitterRepository.forEach((id, sseEmitter) -> send(id, sseEmitter, payload));
  }
}
