package daggerok.config.security.jersey;

import lombok.RequiredArgsConstructor;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.security.Principal;

@RequiredArgsConstructor
public class JerseySecurityContext implements SecurityContext {

  final org.springframework.security.core.context.SecurityContext springSecurityContext;
  final UriInfo uriInfo;

  @Override
  public Principal getUserPrincipal() {
    return springSecurityContext.getAuthentication();
  }

  @Override
  public boolean isUserInRole(final String role) {

    return springSecurityContext.getAuthentication()
                                .getAuthorities()
                                .stream()
                                .anyMatch(o -> o.getAuthority().contains(role));
  }

  @Override
  public boolean isSecure() {

    return uriInfo.getAbsolutePath()
                  .toString()
                  .toLowerCase()
                  .startsWith("https");
  }

  @Override
  public String getAuthenticationScheme() {
    return "spring-security-auth-scheme";
  }
}
