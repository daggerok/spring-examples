package daggerok.event;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;

@Slf4j
//@Service
public class Logger {

  @EventHandler // not a @EventSourcingHandler!
  public void on(Object o) { // any event
    log.info("logging: {}", o.getClass());
  }
}
