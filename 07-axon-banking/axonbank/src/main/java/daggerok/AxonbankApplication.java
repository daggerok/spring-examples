package daggerok;

import daggerok.config.AxonConfig;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@EnableAxon
@SpringBootApplication
@Import({ AxonConfig.class })
public class AxonbankApplication {

  public static void main(String[] args) {
    SpringApplication.run(AxonbankApplication.class, args);
  }
}
