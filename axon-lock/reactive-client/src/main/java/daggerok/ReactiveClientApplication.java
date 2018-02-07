package daggerok;

import daggerok.log.Log;
import daggerok.log.LogRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.function.Function;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
@EnableReactiveMongoRepositories(considerNestedRepositories = true)
public class ReactiveClientApplication {

  final static Function<ServerRequest, MediaType> contentType = request ->
      request.headers().accept()
             // fix java script EventSource which is by default accepts TEXT_EVENT_STREAM
             .contains(TEXT_EVENT_STREAM) ? TEXT_EVENT_STREAM : APPLICATION_STREAM_JSON;

  final Function<URI, UriComponentsBuilder> uriBuilder = uri -> UriComponentsBuilder.newInstance()
                                                                                    .scheme(uri.getScheme())
                                                                                    .host(uri.getHost())
                                                                                    .port(uri.getPort());

  public static void main(String[] args) {
    SpringApplication.run(ReactiveClientApplication.class, args);
  }

  @Bean RouterFunction<ServerResponse> routes(final LogRepository logs) {

    return

        route(POST("/**"),
              request -> created(uriBuilder.apply(request.uri())
                                           .path("/{id}")
                                           .build(1L))
                  .build())

            .andRoute(GET("/{uuid}"),
                      request -> ok().contentType(APPLICATION_JSON_UTF8)
                                     .body(logs.findById(request.pathVariable("uuid")), Log.class))

            .andRoute(GET("/**"),
                      request -> ok()//// old way
                                     //.contentType(contentType(request))
                                     //// functional way
                                     .contentType(contentType.apply(request))
                                     .body(logs.findBy()
                                               .window(20)
                                               .flatMap(s -> s)
//                                     .body(Flux.zip(Flux.interval(Duration.ofMillis(100)),
//                                                    logs.findBy())
//                                               .map(Tuple2::getT2)
                                               .share(), Log.class))
        ;
  }
}
