package daggerok;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static java.util.Collections.singletonMap;

@Slf4j
@Component
public class GlobalErrorHandler implements ExceptionMapper<Throwable> {

  @Override
  public Response toResponse(final Throwable exception) {
    log.error("{}", exception.getLocalizedMessage(), exception);
    return Response.status(Response.Status.BAD_REQUEST)
                   .entity(singletonMap("error", exception.getLocalizedMessage()))
                   .build();
  }
}
