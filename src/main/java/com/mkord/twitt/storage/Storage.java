package com.mkord.twitt.storage;

import com.mkord.twitt.model.Message;

import java.util.Collection;

public interface Storage {
    void storeMessage(Message message);
    Collection<Message> getMessagesForUser(String user);
    void follow(String userRequester, String userWillBeFollowed);
    Collection<String> getFolowingListForUser(String user);
    Collection<Message> timeLineForUser(String user);

}
