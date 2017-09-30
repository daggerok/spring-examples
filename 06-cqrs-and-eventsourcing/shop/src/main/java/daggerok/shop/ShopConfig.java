package daggerok.shop;

import daggerok.shop.data.ShopRepository;
import daggerok.shop.data.domains.Order;
import daggerok.shop.data.domains.ShopItem;
import daggerok.shop.handlers.events.SerializableEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import static java.math.BigDecimal.TEN;
import static java.math.RoundingMode.HALF_UP;

@Configuration
public class ShopConfig {

  @Bean
  public CommandLineRunner testData(final ShopRepository repo) {
    return args -> Stream.of("one", "two", "three")
                         .map(s -> new ShopItem().setName(s)
                                                 .setPrice(new BigDecimal(new Random()
                                                                              .nextFloat()).multiply(TEN)
                                                                                           .setScale(2, HALF_UP)))
                         .forEach(repo::saveShopItem);
  }

  @Bean(name = "eventStore")
  public Map<String, SerializableEvent> eventStore() {
    return new LinkedHashMap<>();
  }

  @Bean(name = "shopItems")
  public Map<String, ShopItem> shopItems() {
    return new HashMap<>();
  }

  @Bean(name = "orders")
  public Map<String, Order> orders() {
    return new HashMap<>();
  }
}
