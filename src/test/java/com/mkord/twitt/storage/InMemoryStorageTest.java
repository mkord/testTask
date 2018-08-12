package com.mkord.twitt.storage;

import com.google.common.collect.ImmutableList;
import com.mkord.twitt.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class InMemoryStorageTest {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    private static final String TEST_USER = "testUser";
    private static final Collection<Message> TEST_USER_MESSAGES = ImmutableList.of(
            new Message(TEST_USER, "message 1 from test user", parseDate("01.01.2015 20:30:40")),
            new Message(TEST_USER, "message 2 from test user", parseDate("02.02.2015 20:30:40")),
            new Message(TEST_USER, "message 3 from test user", parseDate("03.03.2015 20:30:40"))
    );

    private static final String USER_TWO = "userTwo";
    private static final Collection<Message> USER_TWO_MESSAGES = ImmutableList.of(
            new Message(USER_TWO, "message one from user two", parseDate("10.01.2015 20:30:40")),
            new Message(USER_TWO, "message two from user two", parseDate("10.02.2015 20:30:40"))
    );

    private static final String USER_THREE = "userThree";
    private static final Collection<Message> USER_THREE_MESSAGES = ImmutableList.of(
            new Message(USER_THREE, "message one from user three", parseDate("20.01.2015 20:30:40"))
    );

    private static final Collection<String> TEST_USER_FOLOWS = ImmutableList.of(
            USER_THREE, USER_TWO
    );


    @Test
    public void timeLineFor() throws Exception {
        Storage storage = mock(InMemoryStorage.class);
        when(storage.getMessagesForUser(eq(TEST_USER))).thenReturn(TEST_USER_MESSAGES);
        when(storage.getMessagesForUser(eq(USER_TWO))).thenReturn(USER_TWO_MESSAGES);
        when(storage.getMessagesForUser(eq(USER_THREE))).thenReturn(USER_THREE_MESSAGES);
        when(storage.getFolowingListForUser(eq(TEST_USER))).thenReturn(TEST_USER_FOLOWS);
        when(storage.timeLineForUser(eq(TEST_USER))).thenCallRealMethod();

        Collection<Message> timeLine = storage.timeLineForUser(TEST_USER);

        assertThat(timeLine.size()).isEqualByComparingTo(3);
        assertThat(timeLine.stream()
                .filter(m->m.getUser().equals("userTwo"))
                .collect(Collectors.toList()).size()).isEqualByComparingTo(2);
        assertThat(timeLine.stream()
                .filter(m->m.getUser().equals("userThree"))
                .collect(Collectors.toList()).size()).isEqualByComparingTo(1);
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