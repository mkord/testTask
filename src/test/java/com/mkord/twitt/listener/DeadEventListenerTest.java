package com.mkord.twitt.listener;

import com.google.common.eventbus.DeadEvent;
import com.mkord.twitt.AppEventBus;
import com.mkord.twitt.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class DeadEventListenerTest {
    @Autowired
    private AppEventBus appEventBus;

    @Test
    public void testDeadEventHappens() {
        DeadEventListener deadEventListener = mock(DeadEventListener.class);
        appEventBus.register(deadEventListener);
        TestEvent event = new TestEvent();

        appEventBus.post(event);


        verify(deadEventListener, times(1)).
                handleDeadEvent(any(DeadEvent.class));

        verify(deadEventListener).
                handleDeadEvent(argThat(deadEvent -> deadEvent.getEvent().equals(event)));
    }

    private class TestEvent {
        @Override
        public String toString() {
            return "this is test event";
        }
    }

}