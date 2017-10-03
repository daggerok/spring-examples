package daggerok.account;

import daggerok.coreapi.AccountCreatedEvent;
import daggerok.coreapi.CreateAccountCommand;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

  private FixtureConfiguration fixture;

  @Before
  public void setUp() throws Exception {
    fixture = new AggregateTestFixture(Account.class);
//    MyCommandHandler myCommandHandler = new MyCommandHandler(
//        fixture.createGenericRepository(MyAggregate.class)); (2)
//    fixture.registerAnnotatedCommandHandler(myCommandHandler);
  }

  @Test
  public void createAccountTest() throws Exception {
    fixture.givenNoPriorActivity()
           .when(new CreateAccountCommand("12345", 12345))
           .expectEvents(new AccountCreatedEvent("12345", 12345));
  }
}
