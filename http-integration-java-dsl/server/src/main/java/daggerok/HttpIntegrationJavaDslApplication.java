package daggerok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpMethod.POST;

@SpringBootApplication
public class HttpIntegrationJavaDslApplication {

  /**
   * http :8000/ k1=v1 k2=v2
   *
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

  @Bean
  IntegrationFlow upperFlow() {

    return IntegrationFlows.from(Http.inboundGateway("/**")
                                     .requestMapping(m -> m.methods(POST))
                                     .requestPayloadType(String.class))
                           .<String, String>transform(String::toUpperCase)
                           .get();
  }

  public static void main(String[] args) {
    SpringApplication.run(HttpIntegrationJavaDslApplication.class, args);
  }
}
