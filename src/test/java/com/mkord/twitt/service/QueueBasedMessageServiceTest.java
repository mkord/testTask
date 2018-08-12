package com.mkord.twitt.service;

import com.google.common.collect.ImmutableList;
import com.mkord.twitt.Application;
import com.mkord.twitt.model.Message;
import com.mkord.twitt.storage.InMemoryStorage;
import com.mkord.twitt.storage.Storage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class QueueBasedMessageServiceTest {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    private static final String USER_THREE = "userThree";
    private static final String USER_TWO = "userTwo";
    private static Collection<Message> TEST_USER_TIME_LINE = ImmutableList.of(
            new Message(USER_THREE, "message one from user three", parseDate("20.01.2015 20:30:40")),
            new Message(USER_TWO, "message one from user two", parseDate("10.01.2015 20:30:40")),
            new Message(USER_TWO, "message two from user two", parseDate("10.02.2015 20:30:40"))
    );

    @MockBean
    private Storage storage;

    @Autowired
    private MessageService messageService;

    @Test
    public void getTimeLineForUser() throws Exception {
        when(storage.timeLineForUser("testUser")).thenReturn(TEST_USER_TIME_LINE);

        Collection<Message> timeLine = messageService.getTimeLineForUser("testUser");

        List<Message> timeLineLIst = new LinkedList<>(timeLine);

        assertThat(timeLine.size()).isEqualByComparingTo(3);
        assertThat(timeLineLIst.get(0).getUser()).isEqualTo(USER_TWO);
        assertThat(timeLineLIst.get(0).getMessage()).isEqualTo("message two from user two");

        assertThat(timeLineLIst.get(1).getUser()).isEqualTo(USER_THREE);
        assertThat(timeLineLIst.get(1).getMessage()).isEqualTo("message one from user three");

        assertThat(timeLineLIst.get(2).getUser()).isEqualTo(USER_TWO);
        assertThat(timeLineLIst.get(2).getMessage()).isEqualTo("message one from user two");
    }

    private static Date parseDate(String strDate) {
        try {
            return DATE_FORMAT.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in parsing date");
        }
    }

}