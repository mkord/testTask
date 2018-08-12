package com.mkord.twitt.event;

import static com.google.common.base.Preconditions.checkNotNull;

public class NewMessageEvent {

    private final String user;
    private final String message;

    public NewMessageEvent(String user, String message) {
        this.user = checkNotNull(user);
        this.message = checkNotNull(message);
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "NewMessageEvent{" +
                "user='" + user + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
