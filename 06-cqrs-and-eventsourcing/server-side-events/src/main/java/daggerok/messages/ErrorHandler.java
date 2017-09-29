package daggerok.messages;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static daggerok.messages.MessageService.ignores;
import static java.util.Collections.singletonMap;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Map<String, Object>> handleAnyErrors(final Throwable throwable) {

    val error = Try.of(throwable::getLocalizedMessage)
                   .getOrElse("no message");

    if (ignores.stream().filter(throwable.getLocalizedMessage()::contains).count() > 0)
      log.warn("expected sse action: {}", throwable.getLocalizedMessage());
    else log.error("unexpected error: {}", error, throwable);

    return ResponseEntity.badRequest()
                         .body(singletonMap("error", error));
  }
}
