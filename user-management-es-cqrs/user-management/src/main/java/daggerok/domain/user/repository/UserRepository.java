package daggerok.domain.user.repository;

import daggerok.domain.user.User;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface UserRepository {

  void save(final User user);

  User find(final UUID id);
}
