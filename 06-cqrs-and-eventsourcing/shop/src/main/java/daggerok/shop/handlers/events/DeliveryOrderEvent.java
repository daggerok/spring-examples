package daggerok.shop.handlers.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryOrderEvent implements SerializableEvent {

  private static final long serialVersionUID = 4928073684961624065L;

  String transactionId;

  @JsonFormat(pattern = "yyyyMMddHHmmssSSSN", timezone = "UTC")
  final LocalDateTime createdAt = now();
}
