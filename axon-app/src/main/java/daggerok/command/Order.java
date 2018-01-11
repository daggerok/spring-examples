package daggerok.command;

import daggerok.api.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aggregate
@NoArgsConstructor
public class Order {

  @AggregateIdentifier
  private String orderId;
  private Map<String, Long> items;
  private Type type;

  /**
   * create order
   */

  @CommandHandler
  public Order(final CreateOrderCommand command) {

    log.info("\nhandle command: {}", command);

    AggregateLifecycle.apply(
        new OrderCreatedEvent(command.getOrderId(),
                              command.getCreatedAt())
    );
  }

  @EventSourcingHandler
  public void on(final OrderCreatedEvent event) {

    log.info("\nreceive event: {}", event);

    orderId = event.getOrderId();
    items = new ConcurrentHashMap<>();
    type = Type.NEW;
  }

  /**
   * add items to order
   */

  @CommandHandler
  public void handle(final AddItemCommand command) {

    log.info("\nhandle command: {}", command);

    AggregateLifecycle.apply(
        new ItemAddedEvent(orderId,
                           command.getItemId(),
                           command.getQuantity())
    );
  }

  @EventSourcingHandler
  public void on(final ItemAddedEvent event) {

    log.info("\nreceive event: {}", event);

    final String itemId = event.getItemId();

    items.putIfAbsent(itemId, 0L);
    items.put(itemId, items.get(itemId) + event.getQuantity());

    log.info("map after: {}", items);
  }

  /**
   * remove items from order
   */

  @CommandHandler
  public void handle(final RemoveItemCommand command) {

    log.info("\nhandle command: {}", command);

    final String itemId = command.getItemId();
    final long quantity = command.getQuantity();

    if (!items.containsKey(itemId) || items.get(itemId) < quantity)
      throw new NotEnoughItemsToRemoveException(itemId, quantity);

    AggregateLifecycle.apply(
        new ItemRemovedEvent(orderId, itemId, quantity)
    );
  }

  @EventSourcingHandler
  public void on(final ItemRemovedEvent event) {

    log.info("\nreceive event: {}", event);

    final String itemId = event.getItemId();
    items.put(itemId, items.get(itemId) - event.getQuantity());

    log.info("map after: {}", items);
  }

  /**
   * cancel order
   */

  @CommandHandler
  public void handle(final CancelOrderCommand command) {

    if (type == Type.CANCELLED)
      throw new OrderAlreadyCancelledException(orderId);

    AggregateLifecycle.apply(
        new OrderCancelledEvent(orderId)
    );
  }

  @EventSourcingHandler
  public void on(final OrderCancelledEvent event) {

    log.info("\nreceive event: {}", event);
    type = Type.CANCELLED;
    log.info("type after: {}", type);
  }
}
