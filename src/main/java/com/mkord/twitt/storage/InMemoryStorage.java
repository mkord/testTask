package com.mkord.twitt.storage;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.mkord.twitt.model.Message;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class InMemoryStorage implements Storage {

    private final Multimap<String, Message> messages = MultimapBuilder.hashKeys().treeSetValues().build();

    private final Multimap<String, String> followers = MultimapBuilder.hashKeys().hashSetValues().build();

    public void storeMessage(Message message) {
        messages.get(message.getUser()).add(message);
    }

    public Collection<Message> getMessagesForUser(String user) {
        return messages.get(user);
    }

    public void follow(String userRequester, String userWillBeFollowed) {
        followers.get(userRequester).add(userWillBeFollowed);
    }

    public Collection<String> getFolowingListForUser(String user) {
        return followers.get(user);
    }

    public Collection<Message> timeLineForUser(String user) {
        return getFolowingListForUser(user)
                .stream()
                .map(this::getMessagesForUser)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
