package daggerok

import com.mongodb.MongoClient
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle.apply
import org.axonframework.commandhandling.model.Repository
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.eventsourcing.EventSourcingRepository
import org.axonframework.eventsourcing.Snapshotter
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.mongo.DefaultMongoTemplate
import org.axonframework.mongo.MongoTemplate
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.json.JacksonSerializer
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.lang.String.format
import java.util.*
import javax.servlet.http.HttpServletRequest

class CreateCounterCommand(val counterId: String)
class EnableCounterCommand(@TargetAggregateIdentifier val counterId: String)
class DisableCounterCommand(@TargetAggregateIdentifier val counterId: String)
class ResetCounterCommand(@TargetAggregateIdentifier val counterId: String, val to: Int)
class IncrementCounterCommand(@TargetAggregateIdentifier val counterId: String, val amount: Int)
class DecrementCounterCommand(@TargetAggregateIdentifier val counterId: String, val amount: Int)

data class CounterCreatedEvent(val counterId: String? = null)
data class CounterEnabledEvent(val counterId: String? = null)
data class CounterDisabledEvent(val counterId: String? = null)
data class CounterResettedEvent(val counterId: String? = null, val to: Int? = null)
data class CounterIncrementedEvent(val counterId: String? = null, val amount: Int? = null)
data class CounterDecrementedEvent(val counterId: String? = null, val amount: Int? = null)

class CounterDisabledException : RuntimeException()

@Aggregate(repository = "axonCounterRepository")
class CounterAggregator() {

  @AggregateIdentifier
  lateinit var counterId: String
  var enabled: Boolean = false
  var counter: Int = 0

  @CommandHandler
  constructor(cmd: CreateCounterCommand) : this() {
    apply(CounterCreatedEvent(cmd.counterId))
  }

  @EventSourcingHandler
  fun on(evt: CounterCreatedEvent) {
    counterId = evt.counterId!!
  }

  @CommandHandler
  fun handle(cmd: EnableCounterCommand) {
    if (!enabled) apply(CounterEnabledEvent(cmd.counterId))
  }

  @EventSourcingHandler
  fun on(evt: CounterEnabledEvent) {
    enabled = true
  }

  @CommandHandler
  fun handle(cmd: DisableCounterCommand) {
    if (enabled) apply(CounterDisabledEvent(cmd.counterId))
  }

  @EventSourcingHandler
  fun on(evt: CounterDisabledEvent) {
    enabled = false
  }

  @CommandHandler
  fun handle(cmd: IncrementCounterCommand) {
    if (!enabled) throw CounterDisabledException()
    apply(CounterIncrementedEvent(cmd.counterId, cmd.amount))
  }

  @EventSourcingHandler
  fun on(evt: CounterIncrementedEvent) {
    counter += evt.amount!!
  }

  @CommandHandler
  fun handle(cmd: DecrementCounterCommand) {
    if (!enabled) throw CounterDisabledException()
    apply(CounterDecrementedEvent(cmd.counterId, cmd.amount))
  }

  @EventSourcingHandler
  fun on(evt: CounterDecrementedEvent) {
    counter -= evt.amount!!
  }

  @CommandHandler
  fun handle(cmd: ResetCounterCommand) {
    if (!enabled) throw CounterDisabledException()
    apply(CounterResettedEvent(cmd.counterId))
  }

  @EventSourcingHandler
  fun on(evt: CounterResettedEvent) {
    counter = evt.to!!
  }
}

@RestController
@RequestMapping(
    path = ["/api/v1/counter"],
    produces = [APPLICATION_JSON_UTF8_VALUE])
class CounterResource(val commandGateway: CommandGateway) {

  private fun builder(request: HttpServletRequest): UriComponentsBuilder {
    val uri = UriComponentsBuilder.fromHttpUrl(request.requestURL.toString()).build()
    return UriComponentsBuilder.fromHttpUrl(format("%s://%s:%d", uri.scheme, uri.host, uri.port))
  }

  private fun <T> send(cmd: T) = commandGateway.send<T>(cmd)

  @PostMapping
  fun create(request: HttpServletRequest,
             @RequestParam(required = false, name = "counterId") counterId: String?): ResponseEntity<Any> {

    val id = counterId ?: UUID.randomUUID().toString()
    send(CreateCounterCommand(id))

    val uri = builder(request).pathSegment(id).build().toUri()
    return ResponseEntity.created(uri).build()
  }

  @ResponseStatus(ACCEPTED)
  @PutMapping("/{counterId}/enable")
  fun enable(@PathVariable counterId: String) {
    send(EnableCounterCommand(counterId))
  }

  @ResponseStatus(ACCEPTED)
  @PutMapping("/{counterId}/disable")
  fun disable(@PathVariable counterId: String) {
    send(DisableCounterCommand(counterId))
  }

  @ResponseStatus(ACCEPTED)
  @PutMapping("/{counterId}/reset")
  fun reset(@PathVariable counterId: String,
            @RequestParam(required = false, name = "to") to: Int?) {
    send(ResetCounterCommand(counterId, to ?: 0))
  }

  @ResponseStatus(ACCEPTED)
  @PostMapping("/{counterId}/increment")
  fun increment(@PathVariable counterId: String,
                @RequestParam(required = false, name = "amount") amount: Int?) {

    send(IncrementCounterCommand(counterId, amount ?: 1))
  }

  @ResponseStatus(ACCEPTED)
  @PostMapping("/{counterId}/decrement")
  fun decrement(@PathVariable counterId: String,
                @RequestParam(required = false, name = "amount") amount: Int?) {

    send(DecrementCounterCommand(counterId, amount ?: 1))
  }
}

@SpringBootApplication
@EnableMongoRepositories
class AxonCounterApplication {

  @Bean("axonCounterRepository")
  fun axonCounterRepository(eventStore: EventStore, snapshotter: Snapshotter): Repository<CounterAggregator> =
      EventSourcingRepository(CounterAggregator::class.java, eventStore, EventCountSnapshotTriggerDefinition(snapshotter, 4))

  @Bean
  fun snapshotterFactoryBean() = SpringAggregateSnapshotterFactoryBean()

  @Bean
  fun eventStorageEngine(serializer: Serializer, axonMongoTemplate: MongoTemplate): EventStorageEngine = MongoEventStorageEngine(
      serializer, null, axonMongoTemplate, DocumentPerEventStorageStrategy())
      //serializer, null, 4, axonMongoTemplate, DocumentPerCommitStorageStrategy())

  @Bean
  fun serializer(): Serializer = JacksonSerializer()

  @Bean("axonMongoTemplate")
  fun axonMongoTemplate(mongoClient: MongoClient): MongoTemplate =
      DefaultMongoTemplate(mongoClient, "axon")
          .withDomainEventsCollection("events")
          .withSnapshotCollection("snapshots")
}

@RestController
class EventSourcingResource(val mongoClient: MongoClient) {

  @ResponseStatus(OK)
  @GetMapping(path = ["", "/"], produces = [APPLICATION_JSON_UTF8_VALUE])
  fun index(@RequestParam(name = "collection", defaultValue = "snapshots") collection: String) =
      mongoClient.getDatabase("axon")
          .getCollection(collection)
          .find()
}

fun main(args: Array<String>) {
  SpringApplication.run(AxonCounterApplication::class.java, *args)
}
