package com.mkord.twitt;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity<String> entity = new HttpEntity<>(null, headers);



    @Test
    public void testDeadEventListener() throws Exception {

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/",
                HttpMethod.GET, entity, String.class);

        assertThat(response.getBody()).startsWith("Usage");

    }

    @Test
    public void testWall() throws Exception {
        IntStream.range(0, 10).forEach(i -> {
            postMessage("testUserForWall", "message[" + i + "]");
        });

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/wall?user=testUserForWall",
                HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        JSONArray jsonArray = new JSONArray(response.getBody());

        assertThat(jsonArray.length()).isEqualByComparingTo(10);
        assertThat(jsonArray.getJSONObject(0).getString("message")).isEqualTo("message[9]");
        assertThat(jsonArray.getJSONObject(9).getString("message")).isEqualTo("message[0]");
    }


    @Test
    public void testTimeLine() throws Exception {
        prepareDataForTimeLineTest();

        sendFollowRequest("testUser", "userTwo");

        ResponseEntity<String> response = sendTimelineRequest("testUser");

        JSONArray jsonArray = new JSONArray(response.getBody());
        assertThat(jsonArray.length()).isEqualByComparingTo(5);

        sendFollowRequest("testUser", "userThree");

        response = sendTimelineRequest("testUser");

        jsonArray = new JSONArray(response.getBody());
        assertThat(jsonArray.length()).isEqualByComparingTo(9);

        sendFollowRequest("testUser", "userFour");

        response = sendTimelineRequest("testUser");

        jsonArray = new JSONArray(response.getBody());
        assertThat(jsonArray.length()).isEqualByComparingTo(10);

        assertThat(jsonArray.getJSONObject(0).getString("message")).isEqualTo("message[0] from userFour");
        assertThat(jsonArray.getJSONObject(0).getString("user")).isEqualTo("userFour");

        assertThat(jsonArray.getJSONObject(4).getString("message")).isEqualTo("message[1] from userThree");
        assertThat(jsonArray.getJSONObject(4).getString("user")).isEqualTo("userThree");

        assertThat(jsonArray.getJSONObject(9).getString("message")).isEqualTo("message[0] from userTwo");
        assertThat(jsonArray.getJSONObject(9).getString("user")).isEqualTo("userTwo");

    }

    private void prepareDataForTimeLineTest() {
        IntStream.range(0, 2).forEach(i -> {
            postMessage("testUser", "message[" + i + "] from testUser");
        });

        IntStream.range(0, 4).forEach(i -> {
            postMessage("userTwo", "message[" + i + "] from userTwo");
        });

        IntStream.range(0, 2).forEach(i -> {
            postMessage("userThree", "message[" + i + "] from userThree");
        });

        IntStream.range(4, 5).forEach(i -> {
            postMessage("userTwo", "message[" + i + "] from userTwo");
        });

        IntStream.range(3, 5).forEach(i -> {
            postMessage("userThree", "message[" + i + "] from userThree");
        });

        IntStream.range(0, 1).forEach(i -> {
            postMessage("userFour", "message[" + i + "] from userFour");
        });
    }


    private void postMessage(String user, String message) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    makePostMessageUrl(user, message),
                    HttpMethod.GET, entity, String.class);
            assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

            System.out.println(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String makePostMessageUrl(String user, String message) {
        return format("http://localhost:%d/post?user=%s&msg=%s", port, user, message);
    }
    private void sendFollowRequest(String userRequester, String toBeFollowed) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    makeSendFollowUrl(userRequester, toBeFollowed),
                    HttpMethod.GET, entity, String.class);
            assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

            System.out.println(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String makeSendFollowUrl(String userRequester, String userToBeFollowed) {
        return format("http://localhost:%d/follow?userRequester=%s&userToBeFollowed=%s",
                port, userRequester, userToBeFollowed);
    }
    private ResponseEntity<String> sendTimelineRequest(String user) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    makeTimelineUrl(user),
                    HttpMethod.GET, entity, String.class);
            assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String makeTimelineUrl(String user) {
        return format("http://localhost:%d/timeline?user=%s", port, user);
    }
}