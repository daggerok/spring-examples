package daggerok;

//import daggerok.data.ComplaintQueryObject;
//import daggerok.data.ComplaintQueryObjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.spring.config.EnableAxon;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;

//import java.util.UUID;
//import java.util.stream.IntStream;

@Slf4j
@EnableAxon
@SpringBootApplication
public class AxonComplainApplication {
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
    SpringApplication.run(AxonComplainApplication.class, args);
  }
}
