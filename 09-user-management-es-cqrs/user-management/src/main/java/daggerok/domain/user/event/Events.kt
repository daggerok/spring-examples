package daggerok.domain.user.event

import java.time.Instant

data class NicknameChangedEvent(val nickname: String, val `when`: Instant)
data class UserActivatedEvent(val `when`: Instant)
data class UserDeactivatedEvent(val `when`: Instant)
