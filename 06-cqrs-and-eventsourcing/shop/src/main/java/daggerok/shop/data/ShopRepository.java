package daggerok.shop.data;

import daggerok.shop.data.domains.Order;
import daggerok.shop.data.domains.ShopItem;
import daggerok.shop.handlers.events.SerializableEvent;
import daggerok.shop.utils.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ShopRepository {

  static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHhhssSSSN");

  @Qualifier("eventStore")
  final Map<String, SerializableEvent> eventStore;

  @Qualifier("shopItems")
  final Map<String, ShopItem> shopItems;

  @Qualifier("orders")
  final Map<String, Order> orders;

  public List<SerializableEvent> playEvents() {
    return eventStore.values()
                     .stream()
                     .sorted(Comparator.comparing(SerializableEvent::getCreatedAt))
                     .collect(toList());
  }

  public void store(final SerializableEvent event) {
    log.info("store {}", event);
    val timestamp = FORMATTER.format(LocalDateTime.now());
    val key = event.getClass().getSimpleName() + "-" + timestamp;
    eventStore.put(key, event);
  }

  public List<ShopItem> findAllShopItems() {
    return new ArrayList<>(shopItems.values());
  }

  @SneakyThrows
  public ShopItem saveShopItem(final ShopItem shopItem) {
    val id = UUID.create();
    TimeUnit.MILLISECONDS.sleep(113);
    return shopItems.put(id, shopItem.setId(id)
                                     .setCreatedAt(ZonedDateTime.now())
                                     .setLocalDateTime(LocalDateTime.now()));
  }

  public Order getOrder(final String transactionId) {
    return orders.get(transactionId);
  }

  public Order addToCard(final String transactionId, String itemId) {

    orders.computeIfAbsent(
        transactionId,
        s -> new Order().setId(transactionId)
                        .setItemIds(new ArrayList<>())
                        .setCreatedAt(ZonedDateTime.now())
                        .setLocalDateTime(LocalDateTime.now())
    );

    return orders.computeIfPresent(transactionId, (s, order) -> {
      order.getItemIds().add(itemId);
      return order;
    });
  }

  public void shipOrder(final String transactionId) {

    val order = ofNullable(orders.get(transactionId)).orElseThrow(() -> new RuntimeException(transactionId));

    order.getItemIds()
         .stream()
         .map(shopItems::remove)
         .forEach(shopItem -> log.info("publish: {}", shopItem));
  }

  public void closeOrder(final String transactionId) {
    orders.computeIfPresent(transactionId, (s, order) -> order.setDone(true));
  }
}
