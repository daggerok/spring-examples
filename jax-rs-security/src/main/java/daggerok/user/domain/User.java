package daggerok.user.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
public class User /**/implements Serializable {

  private static final long serialVersionUID = -5252172190065816437L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  Long id;

  @NonNull
  String name;
}
