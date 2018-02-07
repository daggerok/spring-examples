package daggerok.entrance;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class EntranceTest {

  FixtureConfiguration<Entrance> fixture;

  @Before
  public void setUp() throws Exception {
    fixture = new AggregateTestFixture<>(Entrance.class);
  }

  /*
    see @AggregateIdentifier in daggerok.entrance.Entrance.entranceId
   */
  @Test
  public void register_entrance() {

    fixture.givenNoPriorActivity()
           .when(new RegisterEntranceCommand("entranceId"))
           .expectEvents(new EntranceRegisteredEvent("entranceId"));
  }

  /*
    see @TargetAggregateIdentifier in daggerok.api.UnlockEntranceCommand.entranceId
   */
  @Test
  public void unlock_entrance() {

    fixture.given(new EntranceRegisteredEvent("entranceId"))
           .when(new UnlockEntranceCommand("entranceId"))
           .expectEvents(new EntranceUnlockedEvent("entranceId"));
  }

  @Test
  public void unlock_unregistered_entrance() {

    fixture.given(new EntranceRegisteredEvent("entranceId"),
                  new EntranceUnregisteredEvent("entranceId"))
           .when(new UnlockEntranceCommand("entranceId"))
           .expectNoEvents()
           .expectException(IllegalStateException.class);
  }

  @Test
  public void lock_entrance() {

    fixture.given(new EntranceRegisteredEvent("entranceId"),
                  new EntranceUnlockedEvent("entranceId"))
           .when(new LockEntranceCommand("entranceId"))
           .expectEvents(new EntranceLockedEvent("entranceId"));
  }

  @Test
  public void unregister_entrance() {

    fixture.given(new EntranceRegisteredEvent("entranceId"),
                  new EntranceUnlockedEvent("entranceId"))
           .when(new UnregisterEntranceCommand("entranceId"))
           .expectEvents(new EntranceUnregisteredEvent("entranceId"));
  }
}
