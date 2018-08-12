package com.mkord.twitt.service;

import com.mkord.twitt.model.Message;

import java.util.Collection;

public interface MessageService {
    void post(String user, String message);

    void follow(String userRequester, String userWillBeFollowed);

    Collection<Message> getMessagesForUser(String user);

    Collection<Message> getTimeLineForUser(String user);
}
