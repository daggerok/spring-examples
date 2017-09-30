package daggerok.shop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.val;

public class UUID {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private UUID() {}

  public static String create() {
    val uuid = java.util.UUID.randomUUID().toString();
    return uuid.substring(uuid.length() - 11);
  }

  public static String stringify(final Object o) {
    return Try.of(() -> OBJECT_MAPPER.writeValueAsString(o))
              .getOrElseGet(throwable -> o.toString());
  }
}
