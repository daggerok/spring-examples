package daggerok.user.rest;

import daggerok.user.data.UserRepository;
import daggerok.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Path("")
@RequiredArgsConstructor
@Produces(APPLICATION_JSON)
public class UserResource {

  final UserRepository userRepository;

  @GET
  @Path("")
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @GET
  @Path("/{id}")
  public User getUser(@PathParam("id") final Long id, @Context SecurityContext securityContext) {

    log.info("{} was here", securityContext.getUserPrincipal().getName());

    return userRepository.findById(id)
                         .orElseThrow(() -> new RuntimeException("not found"));
  }
}
