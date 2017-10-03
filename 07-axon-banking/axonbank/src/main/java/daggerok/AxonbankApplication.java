package daggerok;

import daggerok.config.AxonConfig;
import daggerok.coreapi.CreateAccountCommand;
import daggerok.coreapi.WithdrawMoneyCommand;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@Slf4j
@EnableAxon
@SpringBootApplication
@Import({ AxonConfig.class })
public class AxonbankApplication {

  public static void main(String[] args) {

    val commandBus = SpringApplication.run(AxonbankApplication.class, args).getBean(CommandBus.class);

    commandBus.dispatch(
        asCommandMessage(new CreateAccountCommand("123", 1000)),
        new CommandCallback<Object, Object>() {
          @Override
          public void onSuccess(final CommandMessage<?> commandMessage, final Object result) {
            commandBus.dispatch(asCommandMessage(new WithdrawMoneyCommand("123", 500)));
            commandBus.dispatch(asCommandMessage(new WithdrawMoneyCommand("123", 501)));
          }

          @Override
          public void onFailure(final CommandMessage<?> commandMessage, final Throwable cause) {
            log.warn("=( {}", cause.getLocalizedMessage(), cause);
          }
        });
  }
}
