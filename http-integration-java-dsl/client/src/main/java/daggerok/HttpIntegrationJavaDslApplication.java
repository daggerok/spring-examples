package daggerok;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.http.Http;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpMethod.POST;

@SpringBootApplication
public class HttpIntegrationJavaDslApplication {

  public static void main(String[] args) {
    SpringApplication.run(HttpIntegrationJavaDslApplication.class, args);
  }

  @Configuration
  static class JsonedConfig {

    @MessagingGateway(defaultRequestChannel = "jsonedBean.input")
    interface JsonedGateway {
      String exchange(final String request);
    }

    @Bean
    IntegrationFlow jsonedBean() {

      return flow -> flow
          .handle(Http.outboundGateway("http://127.0.0.1:8000/")
                      .httpMethod(POST)
                      .expectedResponseType(String.class));
    }

    @Bean
    ObjectMapper objectMapper() {
      return new ObjectMapper();
    }

    @RestController
    @RequiredArgsConstructor
    static class MyResource {

      final JsonedGateway gateway;
      final ObjectMapper objectMapper;

      @SneakyThrows
      @PostMapping({ "/*", "/jsoned" })
      String jsoned(@RequestBody final Map<String, String> request) {
        return gateway.exchange(objectMapper.writeValueAsString(request));
      }
    }
  }

  /**
   * string url-encoded and upper cased...
   */
  @Configuration
  static class UrlEncodedConfig {

    @MessagingGateway(defaultRequestChannel = "urlEncoded.input")
    interface UrlEncodedGateway {
      String exchange(final Map<String, String> jsonString);
    }

    @Bean
    IntegrationFlow urlEncoded() {

      return flow -> flow
          .handle(Http.outboundGateway("http://127.0.0.1:8000")
                      .httpMethod(POST)
                      .expectedResponseType(String.class));
    }

    @RestController
    @RequiredArgsConstructor
    static class UrlEncodedResource {

      final UrlEncodedGateway gateway;

      @SneakyThrows
      @PostMapping("url-encoded")
      String urlEncoded(@RequestBody final Map<String, String> request) {
        return gateway.exchange(request);
      }
    }
  }

  /**
   * string just upper cased...
   */
  @Configuration
  static class UpperCasedConfig {

    @MessagingGateway(defaultRequestChannel = "upperCasedBean.input")
    interface UpperCasedGateway {
      String exchange(final String string);
    }

    @Bean
    IntegrationFlow upperCasedBean() {

      return flow -> flow
          .handle(Http.outboundGateway("http://127.0.0.1:8000")
                      .httpMethod(POST)
                      .expectedResponseType(String.class));
    }

    @RestController
    @RequiredArgsConstructor
    static class UpperCasedResource {

      final UpperCasedGateway gateway;

      @SneakyThrows
      @PostMapping("upper-cased")
      String upperCased(@RequestBody final Map<String, String> request) {
        return gateway.exchange(request.getOrDefault("message", "ololo"));
      }
    }
  }
}
