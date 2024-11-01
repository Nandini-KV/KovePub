package com.kognivera.messaging;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PubSubConfig {

  @Bean
  @Profile({"dev-local", "test"})
  CredentialsProvider credentialsProvider() {
    return NoCredentialsProvider.create();
  }

  @Bean
  public MessageChannel userProfileUpdatesChannel() {
    return new DirectChannel();
  }

  @Bean
  @ServiceActivator(inputChannel = "userProfileUpdatesChannel")
  public MessageHandler userProfileUpdatesMessageHandler(PubSubTemplate pubsubTemplate) {
    PubSubMessageHandler adapter =
        new PubSubMessageHandler(pubsubTemplate, "user-profile-updates");

    adapter.setSuccessCallback(((ackId, message) -> log.info(
        "Message was sent via the outbound channel adapter to pubsub topic \"{}\"",
        "user-profile-updates")));

    adapter.setFailureCallback((cause, message) -> {
      log.error("Error sending message to pubsub", cause);
      log.debug("Failed message contents: {}", message.getPayload());
      log.debug("Failed message headers: {}", message.getHeaders());
    });

    return adapter;
  }
}
