package daggerok.guest;

import daggerok.guest.Exceptions.GuestAccessExpiredException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Slf4j
//@Aggregate(repository = "axonGuestRepository")
@Aggregate // see @EnableAutoConfiguration
@NoArgsConstructor
public class Guest {

  @AggregateIdentifier
  String guestId;
  String name;
  LocalDateTime expireAt;
  boolean active;
  String entranceId;

  /* create */

  @CommandHandler
  public Guest(final CreateGuestCommand cmd) {
    apply(new GuestCreatedEvent(cmd.getGuestId(), cmd.getName(), cmd.getExpireAt()));
  }

  @EventSourcingHandler
  public void on(final GuestCreatedEvent event) {
    this.guestId = event.getGuestId();
    this.name = event.getName();
    this.expireAt = event.getExpireAt();
    this.active = false;
  }

  /* activate */

  @CommandHandler
  public void handle(final ActivateGuestCommand cmd) {
    validateExpireAt();
    if (!active)
      apply(new GuestActivatedEvent(cmd.getGiestId()));
  }

  @EventSourcingHandler
  public void on(final GuestActivatedEvent event) {
    this.guestId = event.getGiestId();
    this.active = true;
  }

  /* deactivate */

  @CommandHandler
  public void handle(final DeactivateGuestCommand cmd) {
    validateExpireAt();
    if (active)
      apply(new GuestDeactivatedEvent(cmd.getGiestId()));
  }

  @EventSourcingHandler
  public void on(final GuestDeactivatedEvent event) {
    this.guestId = event.getGiestId();
    this.active = false;
  }

  /* enter */

  @CommandHandler
  public void handle(final GuestEnterCommand cmd) {
    validateExpireAt();
    validateIfActive();
    apply(new GuestEnteredEvent(cmd.getEntranceId(), cmd.getGuestId()));
  }

  @EventSourcingHandler
  public void on(final GuestEnteredEvent event) {
    log.info("guest entering...");
    guestId = event.getGuestId();
    entranceId = event.getEntranceId();
  }

  /* exit */

  @CommandHandler
  public void handle(final GuestExitCommand cmd) {
    validateExpireAt();
    validateIfActive();
    apply(new GuestExitedEvent(cmd.getEntranceId(), cmd.getGuestId()));
  }

  @EventSourcingHandler
  public void on(final GuestExitedEvent event) {
    log.info("guest exiting...");
    guestId = event.getGuestId();
    entranceId = event.getEntranceId();
  }

  /* helpers */

  private void validateExpireAt() {
    if (expireAt.isBefore(now()))
      throw GuestAccessExpiredException.of(guestId, expireAt);
  }

  private void validateIfActive() {
    if (!active)
      throw Exceptions.GuestInactiveException.of(guestId);
  }
}
