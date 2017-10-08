package daggerok.domain.user.event

import java.time.Instant

interface DomainEvent {
  fun at(): Instant
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
