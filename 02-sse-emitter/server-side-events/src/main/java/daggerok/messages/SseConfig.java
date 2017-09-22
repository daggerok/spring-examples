package daggerok.messages;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import static com.fasterxml.jackson.databind.SerializationFeature.*;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

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
/*
    objectMapper.enable(
        WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,
        WRITE_DATE_KEYS_AS_TIMESTAMPS,
        WRITE_DATES_AS_TIMESTAMPS,
        WRITE_DATES_WITH_ZONE_ID,
        WRITE_DURATIONS_AS_TIMESTAMPS
    );

    objectMapper.enable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    objectMapper.enable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
    objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
    objectMapper.enable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
    objectMapper.enable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
*/
    return objectMapper;
  }
}
