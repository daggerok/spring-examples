package daggerok.domain.user;

import daggerok.domain.user.event.DomainEvent;
import daggerok.domain.user.event.NicknameChangedEvent;
import daggerok.domain.user.event.UserActivatedEvent;
import daggerok.domain.user.event.UserDeactivatedEvent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static daggerok.domain.user.UserStatus.*;
import static java.time.Instant.now;
import static lombok.AccessLevel.PRIVATE;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class User {

  List<DomainEvent> changes = new ArrayList<>();

  final UUID id;
  UserStatus state = INITIALIZED;
  String nickname = "";

  public void activate() { // behaviour
    if (isActivated()) // invariant
      throw new IllegalStateException(); // NACK
    //// ACK
    //this.state = ACTIVATED; // changing state
    userActivated(new UserActivatedEvent(now()));
  }

  private void userActivated(final UserActivatedEvent event) {
    state = ACTIVATED;
    changes.add(event);
  }

  public void changeNickname(final String newNickname) {
    if (isDeactivated())
      throw new IllegalStateException();
    nicknameChanged(new NicknameChangedEvent(newNickname, now()));
  }

  private void nicknameChanged(NicknameChangedEvent event) {
    nickname = event.getNickname();
    changes.add(event);
  }

  public void deactivate() {
    if (isDeactivated())
      throw new IllegalStateException();
    //this.state = DEACTIVATED;
    userDeactivated(new UserDeactivatedEvent(now()));
  }

  private void userDeactivated(final UserDeactivatedEvent event) {
    state = DEACTIVATED;
    changes.add(event);
  }

  public boolean isActivated() {
    return ACTIVATED == this.state;
  }

  public boolean isDeactivated() {
    return DEACTIVATED == this.state;
  }

  String getNickname() {
    return this.nickname;
  }
}
