package com.mkord.twitt.storage;

import com.mkord.twitt.model.FollowRequest;
import com.mkord.twitt.model.Message;

import java.util.concurrent.BlockingQueue;

public class StoringFollowRequestRunnable implements Runnable{
    private final BlockingQueue<FollowRequest> queueToStoreFollowRequest;
    private final Storage storage;

    public StoringFollowRequestRunnable(BlockingQueue<FollowRequest> queueToStoreFollowRequest, Storage storage) {
        this.queueToStoreFollowRequest = queueToStoreFollowRequest;
        this.storage = storage;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                FollowRequest followRequestToSave = queueToStoreFollowRequest.take();
                storage.follow(
                        followRequestToSave.getUserRequester(),
                        followRequestToSave.getUserBeFollowed());

                System.out.println("Follow request stored by thread: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.out.println("StoringFollowRequestRunnable -> I am interrupted: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
        }
    }
}
