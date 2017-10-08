package daggerok.domain.user.repository.impl;

import daggerok.domain.user.User;
import daggerok.domain.user.event.DomainEvent;
import daggerok.domain.user.repository.UserRepository;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryEventSourced implements UserRepository {

  final Map<UUID, List<DomainEvent>> db = new ConcurrentHashMap<>();

  @Override
  public void save(final User user) {

    val newChanges = user.getChanges();
    val changes = db.getOrDefault(user.getId(), new ArrayList<>());

    changes.addAll(newChanges);
    db.put(user.getId(), changes);
    user.flush();
  }

  @Override
  public User find(final UUID id) {
    return User.recreatedFrom(id, db.get(id));
  }
}
