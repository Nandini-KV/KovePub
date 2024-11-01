package com.kognivera.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * Base implementation. Update according to utilization needs
 */
@RequiredArgsConstructor
public class CreateUserMessage implements ProfileMessage<UserResponse> {
  private final UserResponse user;

  @Override
  public @NonNull String getUserId() {
    return this.user.getId();
  }

  @Override
  public @NonNull String getPrnId() {
    return user.getPrnId().toString();
  }

  @Override
  public UserResponse getData() {
    return user;
  }

  @Override
  public Map<String, String> getAttributes() {
    return Map.of();
  }

  @Override
  public @NonNull MessageType getMessageType() {
    return MessageType.CREATE_USER_PROFILE;
  }
}
