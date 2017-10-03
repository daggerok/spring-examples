package daggerok.account;

import daggerok.coreapi.AccountCreatedEvent;
import daggerok.coreapi.CreateAccountCommand;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@NoArgsConstructor
public class Account {

  @AggregateIdentifier
  private String accountId;

  @EventSourcingHandler
  public void on(final AccountCreatedEvent event) {
    this.accountId = event.getAccountId();
  }

  @CommandHandler
  public Account(final CreateAccountCommand command) {
    apply(new AccountCreatedEvent(command.getAccountId(), command.getOverdraftLimit()));
  }
}
