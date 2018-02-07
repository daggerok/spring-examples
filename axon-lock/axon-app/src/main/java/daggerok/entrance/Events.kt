package daggerok.entrance

import com.fasterxml.jackson.annotation.JsonAutoDetect

@JsonAutoDetect
data class EntranceRegisteredEvent(val entranceId: String? = null)
data class EntranceUnlockedEvent(val entranceId: String? = null)
data class EntranceLockedEvent(val entranceId: String? = null)
data class EntranceUnregisteredEvent(val entranceId: String? = null)
data class EnteredEvent(val entranceId: String? = null, val guestId: String? = null)
data class ExitedEvent(val entranceId: String? = null, val guestId: String? = null)
