package daggerok.random;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.fasterxml.jackson.databind.util.StdDateFormat.DATE_FORMAT_STR_ISO8601;
import static java.time.ZonedDateTime.now;

@Service
@RequiredArgsConstructor
public class RandomService {

  @Qualifier("randomRepository")
  final Map<String, AtomicLong> randomRepository;

  @Retryable(
      maxAttempts = Integer.MAX_VALUE,
      value = RuntimeException.class,
      backoff = @Backoff(
          delay = 100,
          multiplier = 1.25
      )
  )
  public Map<String, Object> youCanAlwaysMakesMeSmile(final String id) {
    val counter = randomRepository.get(id);
    val message = new Message(counter.incrementAndGet());

    val second = message.getCreatedAt().getSecond();
    if (second % 3 == 0 || second % 5 == 0 || second % 7 == 0 || second % 9 == 0)
      throw new RuntimeException("Oops...");

    reset(id);
    return Collections.singletonMap("message", message);
  }

  @Recover
  public Map<String, Object> recoverFallback() {
    throw new RuntimeException("fuck...");
  }

  public void reset(final String id) {
    val counter = new AtomicLong(0);
    randomRepository.put(id, counter);
  }

  public void clear(final String id) {
    randomRepository.remove(id);
  }

  @Data
  @NoArgsConstructor
  @RequiredArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Message implements Serializable {

    private static final long serialVersionUID = -8919268831237531794L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_STR_ISO8601, timezone = "UTC")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    ZonedDateTime createdAt = now();

    @NonNull
    Long attempts;
  }
}
