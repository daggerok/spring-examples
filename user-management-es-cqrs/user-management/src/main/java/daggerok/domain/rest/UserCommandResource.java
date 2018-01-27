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

    return asList(
        self(id),
        activate(id)
    );
  }

  @PostMapping("/{id}/activate")
  public List<Link> activateUserCommand(@PathVariable final UUID id) {

    val user = userRepository.find(id);

    user.activate();
    userRepository.save(user);

    return asList(
        self(id),
        changeNickname(id, "newNickname")
    );
  }

  @SneakyThrows
  @PostMapping("/{id}/change-nickname/{newNickname}")
  public List<Link> changeNicknameCommand(@PathVariable final UUID id,
                                          @PathVariable("newNickname") final String newNickname) {
    val user = userRepository.find(id);

    user.changeNickname(newNickname);
    userRepository.save(user);

    return asList(
        self(id),
        deactivate(id)
    );
  }

  @PostMapping("/{id}/deactivate")
  public List<Link> deactivateUserCommand(@PathVariable final UUID id) {

    val user = userRepository.find(id);

    user.deactivate();
    userRepository.save(user);

    return asList(
        self(id),
        replay(id)
    );
  }

  @SneakyThrows
  private static Link self(final UUID id) {
    val getUserQuery = UserQueryResource.class.getMethod("getUserQuery", UUID.class);
    return linkTo(getUserQuery, id).withSelfRel();
  }

  @SneakyThrows
  private static Link replay(final UUID id) {
    val replayUserEvents = UserQueryResource.class.getMethod("replayUserEvents", UUID.class);
    return linkTo(replayUserEvents, id).withRel("replay");
  }

  @SneakyThrows
  private static Link activate(final UUID id) {
    val activateCommand = UserCommandResource.class.getMethod("activateUserCommand", UUID.class);
    return linkTo(activateCommand, id).withRel("activate");
  }

  @SneakyThrows
  private static Link changeNickname(final UUID id, final String newNickname) {
    val changeNicknameCommand = UserCommandResource.class.getMethod("changeNicknameCommand", UUID.class, String.class);
    return linkTo(changeNicknameCommand, id, newNickname).withRel("change-nickname");
  }

  @SneakyThrows
  private static Link deactivate(final UUID id) {
    val deactivateCommand = UserCommandResource.class.getMethod("deactivateUserCommand", UUID.class);
    return linkTo(deactivateCommand, id).withRel("deactivate");
  }
}
