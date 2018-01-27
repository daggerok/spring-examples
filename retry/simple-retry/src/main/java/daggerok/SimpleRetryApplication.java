package daggerok;

import daggerok.retry.RetryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RetryConfig.class)
public class SimpleRetryApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimpleRetryApplication.class, args);
  }
}
