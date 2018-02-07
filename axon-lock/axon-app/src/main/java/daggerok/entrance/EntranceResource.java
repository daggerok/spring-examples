package daggerok.entrance;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Map;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/entrance", produces = APPLICATION_JSON_UTF8_VALUE)
public class EntranceResource {

  final CommandGateway commandGateway;

  static String base(final HttpServletRequest request) {

    final UriComponents uri = UriComponentsBuilder.fromHttpUrl(request.getRequestURL()
                                                                      .toString())
                                                  .build();

    return format("%s://%s:%d", uri.getScheme(), uri.getHost(), uri.getPort());
  }

  @PostMapping("/register")
  ResponseEntity register(final HttpServletRequest request,
                          final @RequestBody Map<String, String> body) {

    final String entranceId = body.getOrDefault("entranceId", "");

    commandGateway.send(new RegisterEntranceCommand(entranceId));

    final URI uri = UriComponentsBuilder.fromHttpUrl(base(request))
                                        .pathSegment("api", "v1", "entrance")
                                        .pathSegment(entranceId)
                                        .pathSegment("unlock")
                                        .build()
                                        .toUri();
    return ResponseEntity.created(uri)
                         .build();
  }

  @ResponseStatus(ACCEPTED)
  @PutMapping("/{entranceId}/unlock")
  void unlock(final @PathVariable String entranceId) {
    commandGateway.send(new UnlockEntranceCommand(entranceId));
  }

  @ResponseStatus(ACCEPTED)
  @PutMapping("/{entranceId}/lock")
  void lock(final @PathVariable String entranceId) {
    commandGateway.send(new LockEntranceCommand(entranceId));
  }

  @ResponseStatus(ACCEPTED)
  @DeleteMapping("/{entranceId}")
  void unregister(final @PathVariable String entranceId) {
    commandGateway.send(new UnregisterEntranceCommand(entranceId));
  }

  @ResponseStatus(ACCEPTED)
  @PostMapping("/{entranceId}/enter/{guestId}")
  void enter(final @PathVariable String entranceId,
             final @PathVariable String guestId) {

    commandGateway.send(new EnterCommand(entranceId, guestId));
  }

  @ResponseStatus(ACCEPTED)
  @PostMapping("/{entranceId}/exit/{guestId}")
  void exit(final @PathVariable String entranceId,
            final @PathVariable String guestId) {

    commandGateway.send(new ExitCommand(entranceId, guestId));
  }
}
