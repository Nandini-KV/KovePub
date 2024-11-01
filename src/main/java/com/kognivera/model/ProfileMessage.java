package com.kognivera.model;

import lombok.NonNull;

import java.util.Map;

public interface ProfileMessage<T> {
  @NonNull
  String getUserId();

  @NonNull
  String getPrnId();

  T getData();

  Map<String, String> getAttributes();

  MessageType getMessageType();
}
