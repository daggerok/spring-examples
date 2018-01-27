package daggerok.shop.rest;

import daggerok.shop.data.ShopRepository;
import daggerok.shop.data.domains.Order;
import daggerok.shop.data.domains.ShopItem;
import daggerok.shop.handlers.events.SerializableEvent;
import daggerok.shop.handlers.events.CreateOrderEvent;
import daggerok.shop.handlers.events.ShipOrderEvent;
import daggerok.shop.rest.requests.CreateOrderRequest;
import daggerok.shop.utils.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.ACCEPTED;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShopResource {

  final ShopRepository shopRepository;
  final ApplicationEventPublisher publisher;

  @GetMapping("/api/v1/events")
  public List<SerializableEvent> getEvents() {
    return shopRepository.playEvents();
  }

  @GetMapping("/api/v1/order/items")
  public List<ShopItem> getShopItems() {
    return shopRepository.findAllShopItems();
  }

  @PostMapping({
      "/api/v1/add-to-card",
      "/api/v1/add-to-card/{transaction}",
  })
  @ResponseStatus(ACCEPTED)
  public String addToOrder(@PathVariable Optional<String> transaction, @RequestBody final CreateOrderRequest request) {
    val transactionId = transaction.orElse(UUID.create());
    publisher.publishEvent(new CreateOrderEvent(transactionId, request.getItemIds()));
    return transactionId;
  }

  @GetMapping("/api/v1/order/{transaction}")
  public Order getOrder(@PathVariable String transaction) {
    return shopRepository.getOrder(transaction);
  }

  @ResponseStatus(ACCEPTED)
  @PostMapping("/api/v1/order/{transaction}")
  public void addToOrder(@PathVariable String transaction) {
    publisher.publishEvent(new ShipOrderEvent(transaction));
  }
}
