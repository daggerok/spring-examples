package daggerok.core

import org.axonframework.serialization.Revision
import java.util.*

@Revision("1.0")
data class ComplaintCreatedEvent(val complaintId: UUID, val company: String, val description: String)
