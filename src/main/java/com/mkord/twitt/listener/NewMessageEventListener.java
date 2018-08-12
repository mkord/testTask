package com.mkord.twitt.listener;

import com.google.common.eventbus.Subscribe;
import com.mkord.twitt.event.NewMessageEvent;
import com.mkord.twitt.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewMessageEventListener implements AppEventListener {

    private final MessageService messageService;

    public NewMessageEventListener(@Autowired MessageService messageService) {
        this.messageService = messageService;
    }

    @Subscribe
    public void messagePosted(NewMessageEvent event) {
        messageService.post(event.getUser(), event.getMessage());
    }
}
