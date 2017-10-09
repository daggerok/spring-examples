package daggerok.domain.user.repository;

import daggerok.domain.user.User;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.Instant;
import java.util.UUID;

@NoRepositoryBean
public interface RecreatableStateFromSpecificTime {

  User findFromHistory(final UUID id, final Instant fromTime);
}
