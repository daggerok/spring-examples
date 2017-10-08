package daggerok.core

import daggerok.data.ComplaintQueryObject
import daggerok.data.ComplaintQueryObjectRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class ComplaintResource(val query: ComplaintQueryObjectRepository, val command: CommandGateway) {

  @GetMapping
  fun findAll(): List<ComplaintQueryObject> = query.findAll()

  @GetMapping("/{complaintId}")
  fun findAll(@PathVariable complaintId: UUID): ComplaintQueryObject? {
    return query.findOne(complaintId)
  }

  @PostMapping
  fun createComplaint(@RequestBody request: CreateComplaintRequest): UUID {
    val id = UUID.randomUUID()
    command.send<CreateComplaintCommand>(CreateComplaintCommand(id, request.company!!, request.description!!))
    return id
  }
}
