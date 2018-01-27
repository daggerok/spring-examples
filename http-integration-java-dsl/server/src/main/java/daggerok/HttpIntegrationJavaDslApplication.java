package daggerok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.splitter.DefaultMessageSplitter;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
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

    /**
     * http :8000/ k1=v1 k2=v2
     * <p>
     * <pre>
     *    [
     *     {
     *       "keys": [
     *         "k1",
     *         "k2"
     *       ]
     *     },
     *     {
     *       "values": [
     *         "v1",
     *         "v2"
     *       ]
     *     }
     *   ]
     * </pre>
     *
     * @return flow
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

  @Configuration
  static class SplitterConfig {

    @Bean
    IntegrationFlow splitterFlow() {

      return IntegrationFlows.from(Http.inboundGateway("/split")
                                       .requestMapping(m -> m.methods(POST))
                                       .requestPayloadType(Map.class)
                                          //.convertExceptions(true)
                                       )
                             .<Map, List>transform(m -> asList(
                                 singletonMap("keys", m.keySet()),
                                 singletonMap("values", m.values())
                             ))
                             .split(collectionSplitter()) // will split per items (not sutable for http to get fre responses)
                             .aggregate() // will agregate back all together into response
                             .get();
    }

    @Bean
    DefaultMessageSplitter collectionSplitter() {
      return new DefaultMessageSplitter();
    }
  }
}
