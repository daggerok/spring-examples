package daggerok.config;

import daggerok.GlobalErrorHandler;
import daggerok.domain.User;
import daggerok.domain.UserRepository;
import daggerok.domain.UserResource;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Slf4j
@Configuration
public class JerseyConfig {

  @Bean
  public ApplicationRunner applicationRunner(final UserRepository userRepository) {

    return args -> Stream.of("one", "2", "фри")
                         .map(s -> new User().setName(s))
                         .forEach(userRepository::save);
  }

  @Bean
  public UserResource userResource(final UserRepository userRepository) {
    return new UserResource(userRepository);
  }

  @Bean
  public ResourceConfig resourceConfig(final GlobalErrorHandler globalErrorHandler,
                                       final UserResource userResource) {
    val rc = new ResourceConfig();
    rc.register(userResource);
    rc.register(globalErrorHandler);
    return rc;
  }
}
