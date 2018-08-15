package com.mkord.twitt.storage;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.mkord.twitt.model.Message;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class InMemoryStorage implements Storage {

    private final Map<String, Set<Message>> messages = new ConcurrentHashMap<>();

    private final Map<String, Set<String>> followers = new ConcurrentHashMap<>();

    public void storeMessage(Message message) {
        messages.putIfAbsent(message.getUser(), new HashSet<>());
        messages.get(message.getUser()).add(message);
    }

    public Collection<Message> getMessagesForUser(String user) {
        return messages.get(user);
    }

    public void follow(String userRequester, String userWillBeFollowed) {
        followers.putIfAbsent(userRequester, new HashSet<>());
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
