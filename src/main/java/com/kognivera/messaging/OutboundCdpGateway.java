package com.kognivera.messaging;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;


@MessagingGateway(defaultRequestChannel = "userProfileUpdatesChannel")
public interface OutboundCdpGateway {
  void sendMessage(Message<String> message);
  
}
