package daggerok.api

import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

enum class Type {
  NEW, CANCELLED
}

fun generateId() = UUID.randomUUID().toString()

data class CreateOrderCommand(val orderId: String = generateId(),
                              val createdAt: LocalDateTime = LocalDateTime.now())

data class OrderCreatedEvent(val orderId: String, val createdAt: LocalDateTime)

data class AddItemCommand(@TargetAggregateIdentifier val orderId: String, val itemId: String, val quantity: Long = 1)
data class ItemAddedEvent(val orderId: String, val itemId: String, val quantity: Long)

data class RemoveItemCommand(@TargetAggregateIdentifier val orderId: String, val itemId: String, val quantity: Long = 1)
data class ItemRemovedEvent(val orderId: String, val itemId: String, val quantity: Long)

data class SubmitOrderCommand(@TargetAggregateIdentifier val orderId: String)
data class OrderSubmittedEvent(val orderId: String)

data class CancelOrderCommand(@TargetAggregateIdentifier val orderId: String)
data class OrderCancelledEvent(val orderId: String)

data class PaymentCommand(val paymentId: String = generateId(), @TargetAggregateIdentifier val orderId: String, val amount: BigDecimal)
data class PaymentApprovedEvent(val itemId: String, val orderId: String, val amount: BigDecimal)
data class PaymentCancelledEvent(val itemId: String, val orderId: String)

data class DeliveryCommand(val deliveryId: String = generateId(), @TargetAggregateIdentifier val orderId: String)
data class DeliveryShippedEvent(val deliveryId: String)
data class DeliveryReturnedEvent(val deliveryId: String)

class InvalidOrderException(orderId: String) : RuntimeException()
class NotEnoughItemsToRemoveException(itemId: String, quantity: Long) : RuntimeException()
class OrderAlreadyCancelledException(orderId: String) : RuntimeException()
