package com.javaee.exercises.simple.jms;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import java.util.logging.Level;
import java.util.logging.Logger;

@JMSDestinationDefinition(
        name = "java:global/queue/simpleQ",
        interfaceName = "jakarta.jms.Queue",
        destinationName = "simpleQ"
)
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:global/queue/simpleQ"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "jakarta.jms.Queue")
})
public class MessageConsumer implements MessageListener {
    private static final Logger logger =
            Logger.getLogger(MessageConsumer.class.getName());

    @Override
    public void onMessage(Message message) {
        try {
            logger.info("Message received: " + message.getBody(String.class));
        } catch (JMSException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}