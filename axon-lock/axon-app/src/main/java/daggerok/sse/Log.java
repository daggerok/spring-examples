package daggerok.sse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@Document
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class Log implements Serializable {

  private static final long serialVersionUID = 2391546429822446735L;

  @Id String id;
  @NonNull String type;
  @NonNull Object payload;

  //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime at = now();
}
