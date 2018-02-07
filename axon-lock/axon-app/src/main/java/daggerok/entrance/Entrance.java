package daggerok.entrance;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static java.lang.String.format;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

//@Aggregate(repository = "axonEntranceRepository")
@Aggregate // see @EnableAutoConfiguration
@NoArgsConstructor
public class Entrance {

  @AggregateIdentifier
  String entranceId;
  boolean registered;
  boolean closed;

  /* register */

  @CommandHandler
  public Entrance(final RegisterEntranceCommand cmd) {
    apply(new EntranceRegisteredEvent(cmd.getEntranceId()));
  }

  @EventSourcingHandler
  public void on(final EntranceRegisteredEvent event) {
    this.entranceId = event.getEntranceId();
    this.registered = true;
  }

  /* unlock */

  @CommandHandler
  public void handle(final UnlockEntranceCommand cmd) {
    validate();
    apply(new EntranceUnlockedEvent(cmd.getEntranceId()));
  }

  @EventSourcingHandler
  public void on(final EntranceUnlockedEvent event) {
    closed = false;
  }

  /* lock */

  @CommandHandler
  public void handle(final LockEntranceCommand cmd) {
    apply(new EntranceLockedEvent(cmd.getEntranceId()));
  }

  @EventSourcingHandler
  public void on(final EntranceLockedEvent event) {
    closed = true;
  }

  /* unregister */

  @CommandHandler
  public void handle(final UnregisterEntranceCommand cmd) {
    apply(new EntranceUnregisteredEvent(cmd.getEntranceId()));
  }

  @EventSourcingHandler
  public void on(final EntranceUnregisteredEvent event) {
    registered = false;
  }

  /* enter */

  @CommandHandler
  public void handle(final EnterCommand cmd) {
    validate();
    if (closed)
      throw new IllegalStateException(format("entrance %s is closed.", entranceId));
    apply(new EnteredEvent(cmd.getEntranceId(), cmd.getGuestId()));
  }

  @EventSourcingHandler
  public void on(final EnteredEvent event) { /* do some logic*/ }

  /* enter */

  @CommandHandler
  public void handle(final ExitCommand cmd) {
    validate();
    if (closed)
      throw new IllegalStateException(format("entrance %s is closed.", entranceId));
    apply(new EnteredEvent(cmd.getEntranceId(), cmd.getGuestId()));
  }

  @EventSourcingHandler
  public void on(final ExitedEvent event) { /* do some logic*/ }

  /* helpers */

  private void validate() {
    if (!registered)
      throw new IllegalStateException(format("entrance %s not registered.", entranceId));
  }
}
