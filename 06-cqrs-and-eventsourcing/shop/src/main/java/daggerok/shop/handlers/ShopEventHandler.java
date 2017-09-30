package daggerok.shop.handlers;

import daggerok.shop.data.ShopRepository;
import daggerok.shop.handlers.events.SerializableEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopEventHandler {

  final ShopRepository shopRepository;

  @Async
  @EventListener
  public void store(final SerializableEvent event) {
    shopRepository.store(event);

  }
/*

  @Async
  @EventListener({
      AddToCardEvent.class,
      CreateOrderEvent.class,
      DeliveryOrderEvent.class,
      ShipOrderEvent.class,
  })
  public void onStore(final Serializable event) {
    log.info("onStore: {}", event);
    store(event);
  }

  @Async
  @EventListener
  public void onAddToCard(final AddToCardEvent event) {
    log.info("onAddToCard: {}", event);
    addToCard(event.getTransactionId(), event.getItemId());
  }

  @Async
  @EventListener
  public void onShipOrder(final ShipOrderEvent event) {
    log.info("onShipOrder: {}", event);
    shipOrder(event.getTransactionId());
  }

  @Async
  @EventListener
  public void onCreateOrder(final CreateOrderEvent event) {
    log.info("onCreateOrder: {}", event);
    event.getItemIds().forEach(id -> publisher.publishEvent(new AddToCardEvent(event.getTransactionId(), id)));
  }

  @Async
  @EventListener
  public void onDeliveryOrder(final DeliveryOrderEvent event) {
    log.info("onDeliveryOrder: {}", event);
    closeOrder(event.getTransactionId());
  }
*/
}
