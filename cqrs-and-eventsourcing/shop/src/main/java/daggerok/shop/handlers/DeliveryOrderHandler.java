package daggerok.shop.handlers;

import daggerok.shop.data.ShopRepository;
import daggerok.shop.handlers.events.DeliveryOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryOrderHandler {

  final ShopRepository shopRepository;

  @Async
  @EventListener
  public void onDeliveryOrder(final DeliveryOrderEvent event) {
    log.info("delivery: {}", event);
    shopRepository.closeOrder(event.getTransactionId());
  }
}
