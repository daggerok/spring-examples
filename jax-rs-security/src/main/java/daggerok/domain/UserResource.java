package daggerok.domain;

import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

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
  public User getUser(@PathParam("id") final Long id) {
    return userRepository.findById(id)
                         .orElseThrow(() -> new RuntimeException("not found"));
  }
}
