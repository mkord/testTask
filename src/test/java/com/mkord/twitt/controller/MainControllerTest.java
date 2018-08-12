package com.mkord.twitt.controller;

import com.mkord.twitt.AppEventBus;
import com.mkord.twitt.Application;
import com.mkord.twitt.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AppEventBus appEventBus;

    @MockBean
    private MessageService messageService;


    @Test
    public void testRootEndpointShouldReturnUsageString() throws Exception {

        ResultActions resultActions = mvc.perform(get("/")
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));

        MockHttpServletResponse response = resultActions
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).startsWith("Usage");
    }

    @Test
    public void testPostMessage() throws Exception {
        ResultActions resultActions = mvc.perform(get("/post?user=userTest&msg=text")
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));

        MockHttpServletResponse response = resultActions
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).isEqualTo("Message has been posted");
    }

    @Test
    public void testPostMessageWithoutUser() throws Exception {
        ResultActions resultActions = mvc.perform(get("/post?&msg=text")
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest());

        MockHttpServletResponse response = resultActions
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).isEqualTo("");
    }

    @Test
    public void testPostMessageWithoutMessage() throws Exception {
        ResultActions resultActions = mvc.perform(get("/post?&user=test")
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest());

        MockHttpServletResponse response = resultActions
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).isEqualTo("");
    }
}