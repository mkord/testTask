package com.mkord.twitt;

import com.google.common.eventbus.EventBus;
import org.springframework.stereotype.Component;

@Component
public class AppEventBus {
    private final EventBus eventBus;

    public AppEventBus() {
        eventBus = new EventBus("appEventBus");
    }

    public void post(Object event){
        eventBus.post(event);
    }

    public void register(Object eventListener) {
        eventBus.register(eventListener);
    }

    public EventBus getUnderlayingEventBus() {
        return eventBus;
    }
}
