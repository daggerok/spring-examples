package daggerok.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Configuration
public class SseConfig {

  @Bean(name = "sseEmitterRepository")
  public Map<String, SseEmitter> sseEmitterRepository() {
    return new ConcurrentHashMap<>();
  }

  @Bean
  public ObjectMapper objectMapper() {
    val objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(NON_NULL);
    objectMapper.setSerializationInclusion(NON_EMPTY);
    return objectMapper;
  }
}
