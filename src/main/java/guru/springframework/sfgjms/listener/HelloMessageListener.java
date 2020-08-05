package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Slf4j
@Component
public class HelloMessageListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) {
        log.info(MessageFormat.format("Receiving Msg Id {0}, message: {1}", helloWorldMessage.getId(), helloWorldMessage.getMessage()));

    }
}
