package daggerok.config;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  static UserDetails user(final String username, final String... roles) {

    return User.withUsername(username)
               .password("change-me")
               .roles(roles)
               .build();
  }

  @Bean
  UserDetailsService applicationUserDetailsService() {
    val db = Arrays.asList(
        user("admin", "ADMIN", "USER"),
        user("user", "USER")
    );
    return new InMemoryUserDetailsManager(db);
  }
}
