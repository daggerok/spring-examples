package daggerok.shop.data.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order implements Serializable {

  private static final long serialVersionUID = -8715163103095531860L;

  String id;
  List<String> itemIds;
  boolean done;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS Z", timezone = "UTC")
  ZonedDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS 'Z'", timezone = "UTC")
  LocalDateTime localDateTime;
}
