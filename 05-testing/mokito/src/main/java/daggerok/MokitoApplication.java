package daggerok;

import daggerok.random.RetryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RetryConfig.class)
public class MokitoApplication {

  public static void main(String[] args) {
    SpringApplication.run(MokitoApplication.class, args);
  }
}
