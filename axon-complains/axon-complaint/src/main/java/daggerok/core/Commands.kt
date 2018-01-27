package daggerok.core

import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.util.*

open class CreateComplaintCommand(@TargetAggregateIdentifier val complaintId: UUID, val company: String, val description: String)
