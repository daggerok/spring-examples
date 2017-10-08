package daggerok.core;

import daggerok.data.ComplaintQueryObject;
import daggerok.data.ComplaintQueryObjectRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Data
@Slf4j
@NoArgsConstructor
@Entity//(name = "complaint")
@Aggregate(repository = "complaintRepository")
public class Complaint {

  @Id
  @AggregateIdentifier
  UUID complaintId;

  @Basic(optional = false)
  String company;

  @Basic(optional = false)
  String description;

  @CommandHandler
  public Complaint(final CreateComplaintCommand command) {
    log.info("received command {}", command);
    apply(new ComplaintCreatedEvent(command.getComplaintId(), command.getCompany(), command.getDescription()));
  }

  @EventSourcingHandler
  public void on(final ComplaintCreatedEvent event) {

    log.info("received event {}", event);

    this.complaintId = event.getComplaintId();
    this.company = event.getCompany();
    this.description = event.getDescription();
  }
}
