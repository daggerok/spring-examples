package daggerok.shop.handlers.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderEvent implements SerializableEvent {

  private static final long serialVersionUID = -414848758118990648L;

  String transactionId;
  List<String> itemIds;

  @JsonFormat(pattern = "yyyyMMddHHmmssSSSN", timezone = "UTC")
  final LocalDateTime createdAt = now();
}
