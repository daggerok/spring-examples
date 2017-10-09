package daggerok.domain.rest;

import daggerok.domain.user.User;
import daggerok.domain.user.repository.UserRepository;
import daggerok.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
public class UserQueryResource {

  final UserRepository userRepository;

  @GetMapping("/{id}/replay")
  public List<DomainEvent> replayUserEvents(@PathVariable("id") final UUID id) {
    return getUserQuery(id).getChanges();
  }

  @GetMapping("/{id}")
  public User getUserQuery(@PathVariable("id") final UUID id) {
    return userRepository.find(id);
  }
}
