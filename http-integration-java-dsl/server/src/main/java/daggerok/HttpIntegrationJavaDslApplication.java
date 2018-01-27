package daggerok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

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
  IntegrationFlow flow() {

    return IntegrationFlows.from(Http.inboundGateway("/")
//                                     .messageConverters(
//                                         new FormHttpMessageConverter(),
//                                         new MappingJackson2HttpMessageConverter()
//                                     )
                                     .requestMapping(m -> m.methods(POST)
//                                                           .consumes(APPLICATION_FORM_URLENCODED_VALUE)
                                     )
                                     .requestPayloadType(String.class))
//                           .<Map, List>transform(m -> asList(
//                               singletonMap("keys", m.keySet()),
//                               singletonMap("values", m.values())
//                           ))
                           .<String, String>transform(String::toUpperCase)
                           .get();
  }

  public static void main(String[] args) {
    SpringApplication.run(HttpIntegrationJavaDslApplication.class, args);
  }
}
