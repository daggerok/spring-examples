package daggerok.domain.rest;

import daggerok.domain.user.User;
import daggerok.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
public class UserCommandResource {

  final UserRepository userRepository;

  @PostMapping
  public List<Link> createUserCommand() {

    val id = UUID.randomUUID();
    userRepository.save(new User(id));
    return links(id);
  }

  @PostMapping("/{id}/activate")
  public List<Link> activateUserCommand(@PathVariable final UUID id) {

    val user = userRepository.find(id);
    user.activate();

    userRepository.save(user);
    return links(id);
  }

  @SneakyThrows
  private List<Link> links(final UUID id) {

    val activateCommand = UserCommandResource.class.getMethod("activateUserCommand", UUID.class);
    val getUserQuery = UserQueryResource.class.getMethod("getUserQuery", UUID.class);
    val replayUserEvents = UserQueryResource.class.getMethod("replayUserEvents", UUID.class);

    return asList(
        linkTo(getUserQuery, id).withSelfRel(),
        linkTo(activateCommand, id).withRel("activate"),
        linkTo(replayUserEvents, id).withRel("replay")
    );
  }
}
