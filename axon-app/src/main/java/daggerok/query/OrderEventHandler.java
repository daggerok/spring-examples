package daggerok.query;

import daggerok.api.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderEventHandler {

  final OrderProjectionRepository repository;

  @EventHandler
  @Transactional
  public void on(final OrderCreatedEvent event) {
    repository.save(new OrderView(event.getOrderId(), event.getCreatedAt()));
  }
}
