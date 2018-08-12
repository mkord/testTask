package com.mkord.twitt.controller;

import com.mkord.twitt.AppEventBus;
import com.mkord.twitt.event.FollowRequestEvent;
import com.mkord.twitt.event.NewMessageEvent;
import com.mkord.twitt.model.Message;
import com.mkord.twitt.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class MainController {
    private final AppEventBus appEventBus;
    private final MessageService messageService;

    public MainController(@Autowired AppEventBus appEventBus,
                          @Autowired MessageService messageService) {
        this.appEventBus = appEventBus;
        this.messageService = messageService;
    }

    @RequestMapping("/")
    @ResponseBody String usage() {
        return "Usage:\n<br> to post new message call: http://localhost:8080/post?user=<userName>&msg=<message>" +
                "\n<br> to follow another user call: http://localhost:8080/follow?userRequester=<userName>&userToBeFollowed=<anotherUserName>" +
                "\n<br> to show the wall call: http://localhost:8080/wall?user=<userName>" +
                "\n<br> to show timeline call: http://localhost:8080/timeline?user=<userName>";
    }

    @RequestMapping("post")
    String post(@RequestParam String user, @RequestParam String msg) {
        appEventBus.post(new NewMessageEvent(user, msg));
        return "Message has been posted";
    }

    @RequestMapping("wall")
    Collection<Message> wall(@RequestParam String user) {
        return messageService.getMessagesForUser(user);
    }

    @RequestMapping("timeline")
    Collection<Message> timeline(@RequestParam String user) {
        return messageService.getTimeLineForUser(user);
    }

    @RequestMapping("follow")
    String follow(@RequestParam String userRequester, @RequestParam String userToBeFollowed) {
//        messageService.follow(userRequester, userToBeFollowed);
        appEventBus.post(new FollowRequestEvent(userRequester, userToBeFollowed));
        return "You are following ["+userToBeFollowed+"] from now";
    }
}
