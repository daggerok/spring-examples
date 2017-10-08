package daggerok.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ComplaintQueryObject implements Serializable {

  private static final long serialVersionUID = 6822763887224766781L;

  @Id
  UUID complaintId;
  String company, description;
}
