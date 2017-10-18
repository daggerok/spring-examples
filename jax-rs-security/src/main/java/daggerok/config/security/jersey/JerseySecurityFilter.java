package daggerok.config.security.jersey;

import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

@Component
public class JerseySecurityFilter implements ContainerRequestFilter {

  @Context
  UriInfo uriInfo;

  @Override
  public void filter(final ContainerRequestContext requestContext) throws IOException {

    val springSecurityContext = SecurityContextHolder.getContext();
    val jerseySecurityContext = new JerseySecurityContext(springSecurityContext, uriInfo);

    requestContext.setSecurityContext(jerseySecurityContext);
  }
}
