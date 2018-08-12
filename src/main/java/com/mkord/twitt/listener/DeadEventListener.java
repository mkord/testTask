package com.mkord.twitt.listener;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Component;

@Component
public class DeadEventListener implements AppEventListener {

    @Subscribe
    public void handleDeadEvent(DeadEvent deadEvent) {
        System.out.println("=== Dead Event === " + deadEvent);
    }


}
