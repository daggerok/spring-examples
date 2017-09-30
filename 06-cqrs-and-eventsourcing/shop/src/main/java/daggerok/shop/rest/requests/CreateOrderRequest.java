package daggerok.shop.rest.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderRequest implements Serializable {

  private static final long serialVersionUID = 1103183830434391287L;

  List<String> itemIds;
}
