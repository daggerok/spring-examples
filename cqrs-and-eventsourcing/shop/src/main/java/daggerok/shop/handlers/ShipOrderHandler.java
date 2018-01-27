package daggerok.shop.handlers;

import daggerok.shop.data.ShopRepository;
import daggerok.shop.handlers.events.DeliveryOrderEvent;
import daggerok.shop.handlers.events.ShipOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipOrderHandler {

  final ShopRepository shopRepository;
  final ApplicationEventPublisher publisher;

  @Async
  @EventListener
  public void onShipOrder(final ShipOrderEvent event) {
    log.info("ship: {}", event);
    shopRepository.shipOrder(event.getTransactionId());
    log.info("publish: {}", event);
    publisher.publishEvent(new DeliveryOrderEvent(event.getTransactionId()));
  }
}
