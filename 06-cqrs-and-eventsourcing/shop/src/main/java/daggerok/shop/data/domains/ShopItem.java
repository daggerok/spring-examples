package daggerok.shop.data.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopItem implements Serializable {

  private static final long serialVersionUID = -8491553862807948793L;

  String id;
  String name;
  BigDecimal price;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS Z", timezone = "UTC")
  ZonedDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS 'Z'", timezone = "UTC")
  LocalDateTime localDateTime;
}
