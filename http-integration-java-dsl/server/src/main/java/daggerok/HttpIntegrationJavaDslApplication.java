package daggerok;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.splitter.DefaultMessageSplitter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpMethod.POST;

@SpringBootApplication
public class HttpIntegrationJavaDslApplication {

  public static void main(String[] args) {
    SpringApplication.run(HttpIntegrationJavaDslApplication.class, args);
  }

  @Configuration
  static class UperConfig {

    @Bean
    IntegrationFlow upperFlow() {

      return IntegrationFlows.from(Http.inboundGateway("/**")
                                       .requestMapping(m -> m.methods(POST))
                                       .requestPayloadType(String.class))
          .<String, String>transform(String::toUpperCase)
          .get();
    }
  }

  @Configuration
  static class MapperConfig {

    /*

    http :8000/ k1=v1 k2=v2

    [
      {
        "keys": [
          "k1",
          "k2"
        ]
      },
      {
        "values": [
          "v1",
          "v2"
        ]
      }
    ]

    */

    @Bean
    IntegrationFlow mapperFlow() {

      return IntegrationFlows.from(Http.inboundGateway("/map")
                                       .requestMapping(m -> m.methods(POST))
                                       .requestPayloadType(Map.class))
          .<Map, List>transform(m -> asList(
              singletonMap("keys", m.keySet()),
              singletonMap("values", m.values())
          ))
          .get();
    }
  }

  @Slf4j
  @Configuration
  static class SplitterConfig {

    /*

    http post :8000/split-aggregate k=v kk=vv

    [
        "keys: k, kk",
        "values: v, vv"
    ]

    */

    @Bean
    IntegrationFlow splitterFlow() {

      return IntegrationFlows.from(Http.inboundGateway("/split-aggregate")
                                       .requestMapping(m -> m.methods(POST))
                                       .requestPayloadType(Map.class)
                                     /*.convertExceptions(true)*/)
                             .<Map, List>transform(m -> asList(
                                 singletonMap("keys", m.keySet()),
                                 singletonMap("values", m.values())
                             ))
                             // will split into list of map per items (not sutable for http to get fre responses)
                             .split(collectionSplitter())
                             // do simple transformations..
                             .<Map<String, Collection<String>>, String>transform(e -> e
                                 .entrySet()
                                 .stream()
                                 .map(es -> es.getKey() + ": " + es.getValue()
                                                                   .stream()
                                                                   .collect(joining(", ")))
                                 .collect(joining("; ")))
                             // will agregate back all together into response
                             .aggregate()
                             .get();
    }

    @Bean
    DefaultMessageSplitter collectionSplitter() {
      return new DefaultMessageSplitter();
    }
  }
}
