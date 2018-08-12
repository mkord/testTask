package com.mkord.twitt.listener;

import com.google.common.eventbus.Subscribe;
import com.mkord.twitt.event.FollowRequestEvent;
import com.mkord.twitt.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FollowRequestListener implements AppEventListener {
    private final MessageService messageService;

    public FollowRequestListener(@Autowired MessageService messageService) {
        this.messageService = messageService;
    }

    @Subscribe
    public void followRequested(FollowRequestEvent event) {
        messageService.follow(event.getUserRequester(), event.getUserWillBeFollowed());
    }
}
