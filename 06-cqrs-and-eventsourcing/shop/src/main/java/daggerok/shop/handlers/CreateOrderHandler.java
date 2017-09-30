package daggerok.shop.handlers;

import daggerok.shop.handlers.events.AddToCardEvent;
import daggerok.shop.handlers.events.CreateOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrderHandler {

  final ApplicationEventPublisher publisher;

  @Async
  @EventListener
  public void onCreateOrder(final CreateOrderEvent event) {
    log.info("create: {}", event);
    event.getItemIds().forEach(id -> publisher.publishEvent(new AddToCardEvent(event.getTransactionId(), id)));
  }
}
