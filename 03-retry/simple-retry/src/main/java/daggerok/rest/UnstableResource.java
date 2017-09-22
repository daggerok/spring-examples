package daggerok.rest;

import daggerok.random.RandomService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UnstableResource {

  final RandomService randomService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> unstable() {

    val id = "" + LocalDateTime.now().getLong(ChronoField.NANO_OF_DAY);
    randomService.reset(id);

    val result = Try.of(() -> randomService.youCanAlwaysMakesMeSmile(id));
    randomService.clear(id);

    if (result.isSuccess())
      return ResponseEntity.ok(result.get());

    val error = result.getCause().getLocalizedMessage();
    return ResponseEntity.badRequest()
                         .body(Collections.singletonMap("error", error));
  }
}
