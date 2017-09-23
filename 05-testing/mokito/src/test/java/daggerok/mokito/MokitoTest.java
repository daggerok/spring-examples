package daggerok.mokito;

import daggerok.random.RandomService;
import daggerok.rest.UnstableResource;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@Slf4j
//tag::content[]
@RunWith(MockitoJUnitRunner.class)  /* 1 */
public class MokitoTest {

  @Mock
  RandomService randomService;      /* 2 */

  @InjectMocks
  UnstableResource sut;             /* 3 */

  @Test
  public void testMock() throws Exception {
    val res = sut.unstable();       /* 4 */
    assertThat(res, notNullValue());
  }
}
//tag::content[]
