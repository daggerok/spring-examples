package daggerok.event

import java.time.Instant
import java.util.*

interface DomainEvent {
  fun at(): Instant
}

data class UserInitializedEvent(val id: UUID, private val `when`: Instant) : DomainEvent {
  override fun at(): Instant = `when`
}

data class NicknameChangedEvent(val nickname: String, private val `when`: Instant) : DomainEvent {
  override fun at(): Instant = `when`
}

data class UserActivatedEvent(private val `when`: Instant) : DomainEvent {
  override fun at(): Instant = `when`
}

data class UserDeactivatedEvent(private val `when`: Instant) : DomainEvent {
  override fun at(): Instant = `when`
}
