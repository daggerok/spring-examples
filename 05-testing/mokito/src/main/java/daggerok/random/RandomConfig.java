package daggerok.random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class RandomConfig {

  @Bean(name = "randomRepository")
  public Map<String, AtomicLong> randomRepository() {
    return new ConcurrentHashMap<>();
  }
}
