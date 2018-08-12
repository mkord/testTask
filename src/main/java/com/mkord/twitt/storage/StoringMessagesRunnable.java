package com.mkord.twitt.storage;

import com.mkord.twitt.model.Message;

import java.util.concurrent.BlockingQueue;

public class StoringMessagesRunnable implements Runnable {

    private final BlockingQueue<Message> queueWithMessagesToStore;
    private final Storage storage;

    public StoringMessagesRunnable(BlockingQueue<Message> queueWithMessagesToStore, Storage storage) {
        this.queueWithMessagesToStore = queueWithMessagesToStore;
        this.storage = storage;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message messageToSave = queueWithMessagesToStore.take();
                storage.storeMessage(messageToSave);
                System.out.println("Message stored by thread: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.out.println("StoringMessagesRunnable -> I am interrupted: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
        }
    }
}
