package daggerok.rest;

import daggerok.api.AddItemCommand;
import daggerok.api.CancelOrderCommand;
import daggerok.api.CreateOrderCommand;
import daggerok.api.RemoveItemCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/orders", produces = APPLICATION_JSON_VALUE)
public class OrderResource {

  final CommandGateway commandGateway;

  @PostMapping
  public ResponseEntity createOrder(final HttpServletRequest req) {

    final CreateOrderCommand command = new CreateOrderCommand();

    commandGateway.send(command, LoggingCallback.INSTANCE);

    return ResponseEntity.created(UriComponentsBuilder.fromHttpUrl(req.getRequestURL().toString())
                                                      .pathSegment(command.getOrderId())
                                                      .build()
                                                      .toUri())
                         .build();
  }

  @PutMapping("/{orderId}/add")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void addItem(@PathVariable final String orderId,
                      @RequestBody final LinkedHashMap<String, Long> items) {

    items.entrySet()
         .parallelStream()
         .map(pair -> new AddItemCommand(orderId, pair.getKey(), pair.getValue()))
         .forEach(cmd -> commandGateway.send(cmd, LoggingCallback.INSTANCE));
  }

  @PutMapping("/{orderId}/remove")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void removeItem(@PathVariable final String orderId,
                         @RequestBody final LinkedHashMap<String, Long> items) {

    items.entrySet()
         .parallelStream()
         .map(pair -> new RemoveItemCommand(orderId, pair.getKey(), pair.getValue()))
         .forEach(cmd -> commandGateway.send(cmd, LoggingCallback.INSTANCE));
  }

  @DeleteMapping("/{orderId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void cancelOrder(@PathVariable final String orderId) {
    commandGateway.send(new CancelOrderCommand(orderId), LoggingCallback.INSTANCE);
  }
}
