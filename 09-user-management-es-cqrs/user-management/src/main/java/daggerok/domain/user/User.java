package daggerok.domain.user;

import com.google.common.collect.ImmutableList;
import daggerok.event.*;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static daggerok.domain.user.UserStatus.*;
import static java.time.Instant.now;
import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class User {

  final UUID id;
  String nickname;
  UserStatus state;

  public User(final UUID id) {
    this.id = id;
    on(new UserInitializedEvent(id, now()));
  }

  private User on(final UserInitializedEvent event) {
    nickname = "anonymous";
    state = INITIALIZED;
    changes.add(event);
    return this;
  }

  List<DomainEvent> changes = new ArrayList<>();

  public List<DomainEvent> getChanges() {
    return ImmutableList.copyOf(changes);
  }

  public void activate() { // behaviour
    if (isActivated()) // invariant
      throw new IllegalStateException(); // NACK
    //// ACK
    //this.state = ACTIVATED; // changing state
    on(new UserActivatedEvent(now()));
  }

  private User on(final UserActivatedEvent event) {
    state = ACTIVATED;
    changes.add(event);
    return this;
  }

  public void changeNickname(final String newNickname) {
    if (isDeactivated())
      throw new IllegalStateException();
    on(new NicknameChangedEvent(newNickname, now()));
  }

  private User on(NicknameChangedEvent event) {
    nickname = event.getNickname();
    changes.add(event);
    return this;
  }

  public void deactivate() {
    if (isDeactivated())
      throw new IllegalStateException();
    //this.state = DEACTIVATED;
    on(new UserDeactivatedEvent(now()));
  }

  private User on(final UserDeactivatedEvent event) {
    state = DEACTIVATED;
    changes.add(event);
    return this;
  }

  public boolean isActivated() {
    return ACTIVATED == this.state;
  }

  public boolean isDeactivated() {
    return DEACTIVATED == this.state;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void flush() {
    this.changes.clear();
  }

  // vavr:
  public static User recreatedFrom(final UUID id, final List<DomainEvent> domainEvents) {
    if (CollectionUtils.isEmpty(domainEvents)) return null;
    val initialState = new User(id);
    return io.vavr.collection.List.ofAll(domainEvents).foldLeft(initialState, User::handleStateRecreationVavr);
  }

  private User handleStateRecreationVavr(final DomainEvent domainEvent) {
    return io.vavr.API.Match(domainEvent).of(
        io.vavr.API.Case(io.vavr.API.$(io.vavr.Predicates.instanceOf(UserInitializedEvent.class)), this::on),
        io.vavr.API.Case(io.vavr.API.$(io.vavr.Predicates.instanceOf(NicknameChangedEvent.class)), this::on),
        io.vavr.API.Case(io.vavr.API.$(io.vavr.Predicates.instanceOf(UserActivatedEvent.class)), this::on),
        io.vavr.API.Case(io.vavr.API.$(io.vavr.Predicates.instanceOf(UserDeactivatedEvent.class)), this::on)
    );
  }
/*  // javaslang:
  public static User recreatedFrom(final UUID id, final List<DomainEvent> domainEvents) {
    if (CollectionUtils.isEmpty(domainEvents)) return null;
    val initialState = new User(id);
    return javaslang.collection.List.ofAll(domainEvents).foldLeft(initialState, User::handleStateRecreationJavaslang);
  }

  private User handleStateRecreationJavaslang(final DomainEvent domainEvent) {
    return javaslang.API.Match(domainEvent).of(
        javaslang.API.Case(javaslang.Predicates.instanceOf(UserInitializedEvent.class), this::on),
        javaslang.API.Case(javaslang.Predicates.instanceOf(NicknameChangedEvent.class), this::on),
        javaslang.API.Case(javaslang.Predicates.instanceOf(UserActivatedEvent.class), this::on),
        javaslang.API.Case(javaslang.Predicates.instanceOf(UserDeactivatedEvent.class), this::on)
    );
  }
*/
}
