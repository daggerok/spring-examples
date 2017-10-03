package daggerok.account;

import daggerok.account.err.OverdraftLimitExceededException;
import daggerok.coreapi.AccountCreatedEvent;
import daggerok.coreapi.CreateAccountCommand;
import daggerok.coreapi.MoneyWithdrawnEvent;
import daggerok.coreapi.WithdrawMoneyCommand;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

import static lombok.AccessLevel.PRIVATE;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Data
@Entity
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Aggregate(repository = "accountRepository")
public class Account {

  @Id
  @AggregateIdentifier
  String accountId;

  @Basic
  int balance;

  @Basic
  int overdraftLimit;

  @CommandHandler
  public Account(final CreateAccountCommand command) {
    apply(new AccountCreatedEvent(command.getAccountId(), command.getOverdraftLimit()));
  }

  @EventSourcingHandler
  public void on(final AccountCreatedEvent event) {
    this.accountId = event.getAccountId();
    this.overdraftLimit = event.getOverdraftLimit();
  }

  @EventSourcingHandler
  public void on(final MoneyWithdrawnEvent event) {
    this.balance = event.getBalance();
  }

  @CommandHandler
  public void handle(final WithdrawMoneyCommand command) {
    if (balance + overdraftLimit < command.getAmount()) throw new OverdraftLimitExceededException();
    apply(new MoneyWithdrawnEvent(accountId, command.getAmount(), balance - command.getAmount()));
  }
}
