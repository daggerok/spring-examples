package daggerok.guest

import com.fasterxml.jackson.annotation.JsonAutoDetect
import java.time.LocalDateTime

@JsonAutoDetect
data class GuestCreatedEvent(val guestId: String? = null,
                             val name: String? = null,
                             val expireAt: LocalDateTime? = null)

data class GuestActivatedEvent(val giestId: String? = null)
data class GuestDeactivatedEvent(val giestId: String? = null)
data class GuestEnteredEvent(val entranceId: String? = null, val guestId: String? = null)
data class GuestExitedEvent(val entranceId: String? = null, val guestId: String? = null)
