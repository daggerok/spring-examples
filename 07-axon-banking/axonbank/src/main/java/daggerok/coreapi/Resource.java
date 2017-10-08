package daggerok.coreapi;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class Resource {

  final CommandGateway commandGateway;

  @PostMapping
  public UUID create(@RequestBody final CreateAccountRequest req) {
    val id = UUID.randomUUID();
    commandGateway.send(new CreateAccountCommand(id.toString(), req.getOverdraftLimit()));
    return id;
  }
}
