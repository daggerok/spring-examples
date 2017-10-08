package daggerok.domain.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static daggerok.domain.user.UserStatus.*;
import static lombok.AccessLevel.PRIVATE;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class User {

  final UUID id;
  UserStatus state = INITIALIZED;
  String nickname = "";

  public void activate() {
    if (isActivated())
      throw new IllegalStateException();
    this.state = ACTIVATED;
  }

  public void deactivate() {
    if (isDeactivated())
      throw new IllegalStateException();
    this.state = DEACTIVATED;
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

  public void changeNickname(final String newNickname) {
    if (isDeactivated())
      throw new IllegalStateException();
    nickname = newNickname;
  }
}
