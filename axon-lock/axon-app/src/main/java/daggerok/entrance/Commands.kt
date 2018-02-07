package daggerok.entrance

import org.axonframework.commandhandling.TargetAggregateIdentifier

class RegisterEntranceCommand(val entranceId: String)
class UnlockEntranceCommand(@TargetAggregateIdentifier val entranceId: String)
class LockEntranceCommand(@TargetAggregateIdentifier val entranceId: String)
class UnregisterEntranceCommand(@TargetAggregateIdentifier val entranceId: String)
class EnterCommand(@TargetAggregateIdentifier val entranceId: String, val guestId: String)
class ExitCommand(@TargetAggregateIdentifier val entranceId: String, val guestId: String)
