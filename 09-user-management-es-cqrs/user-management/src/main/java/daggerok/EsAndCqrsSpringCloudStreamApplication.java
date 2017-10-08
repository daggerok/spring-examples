package daggerok;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EsAndCqrsSpringCloudStreamApplication {
  /*
    @Bean
    CommandLineRunner init(final ComplaintQueryObjectRepository repository) {
      return args -> IntStream.range(1, 3)
                              .mapToObj(operand -> new ComplaintQueryObject(UUID.randomUUID(),
                                                                            "company " + operand,
                                                                            operand + " wtf?!"))
                              .forEach(repository::save);
    }
  */
  public static void main(String[] args) {
    SpringApplication.run(EsAndCqrsSpringCloudStreamApplication.class, args);
  }
}
