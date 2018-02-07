package daggerok.log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Document
@NoArgsConstructor
public class Log implements Serializable {

  private static final long serialVersionUID = 2391546429822446735L;

  @Id String id;
  String type;
  Object payload;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime at;
}
