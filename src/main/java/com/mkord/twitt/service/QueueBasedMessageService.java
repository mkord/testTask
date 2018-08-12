package com.mkord.twitt.service;

import com.mkord.twitt.model.FollowRequest;
import com.mkord.twitt.model.Message;
import com.mkord.twitt.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class QueueBasedMessageService implements MessageService {

    private final Storage storage;
    private final BlockingQueue<Message> queueToStoreMessages;
    private final BlockingQueue<FollowRequest> queueToStoreFollowRequest;

    public QueueBasedMessageService(@Autowired Storage storage,
                                    @Autowired BlockingQueue<Message> queueToStoreMessages,
                                    @Autowired BlockingQueue<FollowRequest> queueToStoreFollowRequest) {
        this.storage = storage;
        this.queueToStoreMessages = queueToStoreMessages;
        this.queueToStoreFollowRequest = queueToStoreFollowRequest;
    }

    @Override
    public void post(String user, String message) {
        checkNotNull(user);
        checkNotNull(message);

        CompletableFuture
                .runAsync(() -> storeMessage(user, message))
        ;
    }

    @Override
    public void follow(String userRequester, String userWillBeFollowed) {
        checkNotNull(userRequester);
        checkNotNull(userWillBeFollowed);

        CompletableFuture
                .runAsync(() -> storeFollowRequest(userRequester, userWillBeFollowed))
        ;
    }

    @Override
    public Collection<Message> getMessagesForUser(String user) {
        checkNotNull(user);
        return storage.getMessagesForUser(user);
    }

    @Override
    public Collection<Message> getTimeLineForUser(String user) {
        checkNotNull(user);
        return storage.timeLineForUser(user).stream().sorted().collect(Collectors.toList());
    }

    private void storeMessage(String user, String message) {
        try {
            queueToStoreMessages.put(new Message(user, message, new Date()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void storeFollowRequest(String userRequester, String userWillBeFollowed) {
        try {
            queueToStoreFollowRequest.put(new FollowRequest(userRequester, userWillBeFollowed));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
