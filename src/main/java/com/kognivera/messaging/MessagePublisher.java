package com.kognivera.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.kognivera.model.ProfileMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * CDP publisher class to be used within profile service. It sends messages to CDP pubsub topic.
 * Since this class takes care of message ordering necessary for correct delivery, this should be
 * used as the only method of sending updates to CDP
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessagePublisher {
  private final OutboundCdpGateway messageGateway;
  private final ObjectMapper objectMapper;

  /**
   * Publishes a message to CDP updates pubsub topic taking care of ordering, serialization and
   * attributes setting.
   *
   * @param message The message to be sent. Must implement ProfileMessage interface
   * @return boolean indicating operation success
   * @param <T> message payload type
   */
  public <T> boolean publish(ProfileMessage<T> message) {
    try {
      String serializedData = objectMapper.writeValueAsString(message.getData());

      MessageBuilder<String> pubSubMessageBuilder = MessageBuilder.withPayload(serializedData)
          .setHeader(GcpPubSubHeaders.ORDERING_KEY, message.getUserId())
          .setHeader("prnId", message.getPrnId())
          .setHeader("messageType", message.getMessageType().asString());
      message.getAttributes().forEach(pubSubMessageBuilder::setHeader);
      messageGateway.sendMessage(pubSubMessageBuilder.build());
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      return false;
    }
    return true;
  }
}
