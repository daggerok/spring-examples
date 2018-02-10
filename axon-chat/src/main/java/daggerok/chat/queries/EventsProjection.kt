package daggerok.rest

import com.mongodb.MongoClient
import org.axonframework.eventsourcing.eventstore.EventStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors.toList

@RestController
class EventsProjection(val eventStore: EventStore,
                       val mongoClient: MongoClient,
                       @Value("\${spring.datasource.name}") val name: String) {

  @GetMapping("/{aggregateId}")
  @Transactional(readOnly = true)
  fun listEvents(@PathVariable aggregateId: String) = eventStore
      .readEvents(aggregateId)
      .asStream()
      .collect(toList())

  @ResponseStatus(HttpStatus.OK)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @GetMapping(path = ["", "/"], produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
  fun index(@RequestParam(name = "collection", defaultValue = "snapshots") collection: String) =
      mongoClient.getDatabase(name)
          .getCollection(collection)
          .find()
}
