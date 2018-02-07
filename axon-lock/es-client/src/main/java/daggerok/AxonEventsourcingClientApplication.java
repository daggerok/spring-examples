package daggerok;

import com.mongodb.MongoClient;
import io.vavr.control.Try;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.net.URI;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/*
$ mongo

> show dbs
axonframework  0.000GB

> use axonframework
switched to db axonframework

> show collections
domainevents
snapshotevents

> db.domainevents.find().pretty()
{
	"_id" : ObjectId("5a7a401abc70441bd224661d"),
	"aggregateIdentifier" : "main",
	"type" : "Entrance",
	"sequenceNumber" : NumberLong(0),
	"serializedPayload" : "{\"entranceId\":\"main\"}",
	"timestamp" : "2018-02-06T23:54:02.373Z",
	"payloadType" : "daggerok.entrance.EntranceRegisteredEvent",
	"payloadRevision" : null,
	"serializedMetaData" : "{\"traceId\":\"ba7dbfeb-501a-4d1a-ac98-46e1b0ec4ac9\",\"correlationId\":\"ba7dbfeb-501a-4d1a-ac98-46e1b0ec4ac9\"}",
	"eventIdentifier" : "a70416a8-97df-4267-b582-09c66be7d13c"
}
{
	"_id" : ObjectId("5a7a40c8bc70441bd224661f"),
	"aggregateIdentifier" : "reception",
	"type" : "Entrance",
	"sequenceNumber" : NumberLong(0),
	"serializedPayload" : "{\"entranceId\":\"reception\"}",
	"timestamp" : "2018-02-06T23:56:56.650Z",
	"payloadType" : "daggerok.entrance.EntranceRegisteredEvent",
	"payloadRevision" : null,
	"serializedMetaData" : "{\"traceId\":\"a6b8607d-3aec-4ea8-a556-75b18e905568\",\"correlationId\":\"a6b8607d-3aec-4ea8-a556-75b18e905568\"}",
	"eventIdentifier" : "5e19051e-f7a7-414f-bf3b-9b88e58f7315"
}

> db.snapshotevents.find().pretty() // TODO
*/

@SpringBootApplication
@EnableReactiveMongoRepositories(considerNestedRepositories = true)
public class AxonEventsourcingClientApplication {

  final Function<URI, UriComponentsBuilder> uriBuilder = uri -> UriComponentsBuilder.newInstance()
                                                                                    .scheme(uri.getScheme())
                                                                                    .host(uri.getHost())
                                                                                    .port(uri.getPort());
  public static void main(String[] args) {
    SpringApplication.run(AxonEventsourcingClientApplication.class, args);
  }

  final static MediaType contentType(final ServerRequest request) {
    return request.headers().accept()
                  // if headers contains accept:application/json, then use it, otherwise: stream
                  .contains(APPLICATION_JSON) ? APPLICATION_JSON : APPLICATION_STREAM_JSON;
  }

  @Bean RouterFunction<ServerResponse> routes(final MongoClient mongoClient) {

    return

        route(GET("/api/v1/axon/{collection}"),
              request -> ok().contentType(/* APPLICATION_JSON */ contentType(request))
                             .body(Flux.fromStream(StreamSupport.stream(mongoClient.getDatabase("axonframework")
                                                                                   .getCollection(request.pathVariable(
                                                                                       "collection"))
                                                                                   .find()
                                                                                   .spliterator(), true))
                                       .subscribeOn(Schedulers.elastic()), Document.class))

            .andRoute(GET("/**"),
                      request -> ok().contentType(APPLICATION_JSON_UTF8)
                                     .body(WebClient.create(uriBuilder.apply(request.uri())
                                                                      .build()
                                                                      .toString())
                                                    .get()
                                                    .uri("/api/v1/axon/{collection}", request.queryParam("collection")
                                                                                             .orElse("domainevents"))
                                                    //.uri("/api/v1/axon/{collection}", "snapshotevents")
                                                    .accept(contentType(request))
                                                    .exchange()
                                                    .flatMapMany(response -> response.bodyToMono(String.class))
                                                    .takeLast(1),
                                           String.class))
        ;
  }
}
