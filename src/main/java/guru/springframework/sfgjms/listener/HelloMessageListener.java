package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.text.MessageFormat;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) {
        log.info(MessageFormat.format("Receiving Msg Id {0}, message: {1}", helloWorldMessage.getId(), helloWorldMessage.getMessage()));

    }

    @JmsListener(destination = JmsConfig.SEND_AND_RECEIVE_QUEUE)
    public void listenForYo(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) throws JMSException {
        HelloWorldMessage payLoadMsg = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Gaba Gaba!")
                .build();

        log.info(helloWorldMessage.getMessage());

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payLoadMsg);

    }
}
