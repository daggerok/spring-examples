package daggerok.domain.user.repository.impl;

import daggerok.domain.user.User;
import daggerok.domain.user.event.DomainEvent;
import daggerok.domain.user.repository.RecreatableStateFromSpecificTime;
import daggerok.domain.user.repository.UserRepository;
import lombok.val;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class UserRepositoryEventSourced implements UserRepository, RecreatableStateFromSpecificTime {

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
    val all = db.getOrDefault(id, emptyList());
    return User.recreatedFrom(id, all);
  }

  @Override
  public User findFromHistory(final UUID id, final Instant pointOfTime) {

    val previous = db.getOrDefault(id, emptyList())
                     .stream()
                     .filter(domainEvent -> domainEvent.at().compareTo(pointOfTime) <= 0)
                     .collect(Collectors.toList());

    return User.recreatedFrom(id, previous);
  }
}
