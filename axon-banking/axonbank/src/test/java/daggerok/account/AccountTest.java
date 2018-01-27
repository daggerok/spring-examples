package daggerok.account;

import daggerok.account.err.OverdraftLimitExceededException;
import daggerok.coreapi.AccountCreatedEvent;
import daggerok.coreapi.CreateAccountCommand;
import daggerok.coreapi.MoneyWithdrawnEvent;
import daggerok.coreapi.WithdrawMoneyCommand;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {

  private FixtureConfiguration fixture;

  @Before
  public void setUp() throws Exception {
    fixture = new AggregateTestFixture(Account.class);
    /*
    MyCommandHandler myCommandHandler = new MyCommandHandler(
        fixture.createGenericRepository(MyAggregate.class)); (2)
    fixture.registerAnnotatedCommandHandler(myCommandHandler);
    */
  }

  @Test
  public void createAccountTest() throws Exception {
    fixture.givenNoPriorActivity()
           .when(new CreateAccountCommand("12345", 12345))
           .expectEvents(new AccountCreatedEvent("12345", 12345));
  }

  @Test
  public void testWithdrawReasonableAmount() throws Exception {
    fixture.given(new AccountCreatedEvent("12345", 12345))
           .when(new WithdrawMoneyCommand("12345", 5))
           .expectEvents(new MoneyWithdrawnEvent("12345", 5, -5));
  }

  @Test
  public void testWithdrawAbsurdAmount() throws Exception {
    fixture.given(new AccountCreatedEvent("12345", 12345))
           .when(new WithdrawMoneyCommand("12345", 12346))
           .expectNoEvents()
           .expectException(OverdraftLimitExceededException.class);
  }

  @Test
  public void testWithdrawTwice() throws Exception {
    fixture.given(new AccountCreatedEvent("12345", 12345),
                  new MoneyWithdrawnEvent("12345", 10000, -10000))
           .when(new WithdrawMoneyCommand("12345", 2346))
           .expectNoEvents()
           .expectException(OverdraftLimitExceededException.class);
  }
}
