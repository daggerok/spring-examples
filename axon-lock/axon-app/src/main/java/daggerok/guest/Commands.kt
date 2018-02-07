package daggerok.guest

import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.time.LocalDateTime

class CreateGuestCommand(val guestId: String,
                         val name: String,
                         val expireAt: LocalDateTime)

class ActivateGuestCommand(@TargetAggregateIdentifier val giestId: String)
class DeactivateGuestCommand(@TargetAggregateIdentifier val giestId: String)
class GuestEnterCommand(val entranceId: String, @TargetAggregateIdentifier val guestId: String)
class GuestExitCommand(val entranceId: String, @TargetAggregateIdentifier val guestId: String)
