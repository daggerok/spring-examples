package daggerok.domain.user.repository.impl;

import daggerok.domain.user.User;
import daggerok.domain.user.repository.UserRepository;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class UserRepositoryInMemory implements UserRepository {

  final Map<UUID, User> db = new ConcurrentHashMap<>();

  @Override
  public void save(final User user) {
    db.put(user.getId(), user);
  }

  @Override
  public User find(final UUID id) {
    return db.get(id);
  }
}
