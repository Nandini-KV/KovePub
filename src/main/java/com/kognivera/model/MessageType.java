package com.kognivera.model;

public enum MessageType {
    UPDATE_USER_PROFILE("update_user_profile"),
    CREATE_USER_PROFILE("create_user_profile"),
    UPDATE_ADDRESS("update_address"),
    CREATE_ADDRESS("create_address");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    public String asString() {
        return type;
    }
}
