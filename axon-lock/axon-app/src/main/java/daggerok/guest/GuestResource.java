package daggerok.guest;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/guest", produces = APPLICATION_JSON_UTF8_VALUE)
public class GuestResource {

  final CommandGateway commandGateway;

  static String base(final HttpServletRequest request) {

    final UriComponents uri = UriComponentsBuilder.fromHttpUrl(request.getRequestURL()
                                                                      .toString())
                                                  .build();

    return format("%s://%s:%d", uri.getScheme(), uri.getHost(), uri.getPort());
  }

  @PostMapping("/register")
  ResponseEntity register(final @RequestBody Map<String, String> body,
                          final HttpServletRequest request) {

    final String name = body.getOrDefault("name", "anonymous");
    final String id = UUID.randomUUID().toString();
    final LocalDateTime expireTomorrow = LocalDateTime.now().plusDays(1);

    commandGateway.send(new CreateGuestCommand(id, name, expireTomorrow));

    final URI uri = UriComponentsBuilder.fromHttpUrl(base(request))
                                        .pathSegment("api", "v1", "guest")
                                        .pathSegment(id)
                                        .pathSegment("activate")
                                        .build()
                                        .toUri();
    return ResponseEntity.created(uri)
                         .build();
  }

  @ResponseStatus(ACCEPTED)
  @PutMapping("/{guestId}/activate")
  void activate(final @PathVariable String guestId) {
    commandGateway.send(new ActivateGuestCommand(guestId));
  }

  @ResponseStatus(ACCEPTED)
  @PutMapping("/{guestId}/deactivate")
  void deactivate(final @PathVariable String guestId) {
    commandGateway.send(new DeactivateGuestCommand(guestId));
  }
}
