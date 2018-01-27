package daggerok.shop.handlers.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface SerializableEvent extends Serializable {

  @JsonProperty
  default String type() {
    return getClass().getSimpleName();
  }

  LocalDateTime getCreatedAt();
}
