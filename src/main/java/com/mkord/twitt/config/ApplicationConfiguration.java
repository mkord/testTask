package com.mkord.twitt.config;

import com.mkord.twitt.AppEventBus;
import com.mkord.twitt.listener.AppEventListener;
import com.mkord.twitt.model.FollowRequest;
import com.mkord.twitt.model.Message;
import com.mkord.twitt.storage.Storage;
import com.mkord.twitt.storage.StoringFollowRequestRunnable;
import com.mkord.twitt.storage.StoringMessagesRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.IntStream;

@Configuration
@EnableAsync
public class ApplicationConfiguration {

    @Autowired
    private List<AppEventListener> listeners;

    @Autowired
    private AppEventBus appEventBus;

    @Autowired
    private Storage storage;

    @Bean
    public BlockingQueue<Message> queueToStoreMessages() {
        return new LinkedBlockingDeque<>(100);
    }

    @Bean
    public BlockingQueue<FollowRequest> queueToStoreFollowRequest() {
        return new LinkedBlockingDeque<>(100);
    }

    @PostConstruct
    public void registerAllListeners() {
        listeners.forEach(l-> appEventBus.register(l));
        IntStream.range(0,5).forEach(i ->
                storingMessagesThreadExecutor()
                .execute(new StoringMessagesRunnable(queueToStoreMessages(), storage)));
        IntStream.range(0,3).forEach(i ->
                followRequestThreadExecutor()
                .execute(new StoringFollowRequestRunnable(queueToStoreFollowRequest(), storage)));
    }

    @PreDestroy
    public void preDestroy() {
        storingMessagesThreadExecutor().shutdownNow();
        followRequestThreadExecutor().shutdownNow();
    }

    @Bean
    public ExecutorService storingMessagesThreadExecutor() {
        return Executors.newFixedThreadPool(5);
    }

    @Bean
    public ExecutorService followRequestThreadExecutor() {
        return Executors.newFixedThreadPool(5);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
