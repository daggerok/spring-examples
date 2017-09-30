package daggerok.shop.handlers;

import daggerok.shop.data.ShopRepository;
import daggerok.shop.handlers.events.AddToCardEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddToCardHandler {

  final ShopRepository shopRepository;

  @Async
  @EventListener
  public void onAddToCard(final AddToCardEvent event) {
    log.info("add: {}", event);
    shopRepository.addToCard(event.getTransactionId(), event.getItemId());
  }
}
