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
public class AddToCardEvent implements SerializableEvent {

  private static final long serialVersionUID = -4622640737816504537L;

  String transactionId;
  String itemId;

  @JsonFormat(pattern = "yyyyMMddHHmmssSSSN", timezone = "UTC")
  final LocalDateTime createdAt = now();
}
