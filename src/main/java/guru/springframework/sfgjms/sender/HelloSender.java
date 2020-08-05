package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.text.MessageFormat;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Yo!")
                .build();

        final Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.SEND_AND_RECEIVE_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                }

                return helloMessage;

            }
        });

        log.info(MessageFormat.format("Message received {0}", receivedMsg.getBody(String.class)));

    }
}
