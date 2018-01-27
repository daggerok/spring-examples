package daggerok.domain.rest;

import daggerok.domain.user.User;
import daggerok.domain.user.repository.UserRepository;
import daggerok.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
public class UserQueryResource {

  final UserRepository userRepository;

  @GetMapping("/{id}/replay")
  public List<Map<String, ?>> replayUserEvents(@PathVariable("id") final UUID id) {
    return getUserQuery(id).getChanges()
                           .stream()
                           .map(event -> singletonMap(event.getClass().getSimpleName(), event))
                           .collect(toList());
  }

  @GetMapping("/{id}")
  public User getUserQuery(@PathVariable("id") final UUID id) {
    return userRepository.find(id);
  }
}
