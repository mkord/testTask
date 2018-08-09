package com.mkord.twitt.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/")
    String usage() {
        return "Usage text will be here";
    }

    @RequestMapping("post")
    String post(@RequestParam String user, @RequestParam String msg) {
        System.out.println("user = [" + user + "], msg = [" + msg + "]");
        return "user = [" + user + "], msg = [" + msg + "]";
    }

    @RequestMapping("wall")
    String wall(@RequestParam String user) {
        System.out.println("user = [" + user + "]");
        return "The wall for the user = [" + user + "]";
    }

    @RequestMapping("timeline")
    String timeline(@RequestParam String user) {
        System.out.println("user = [" + user + "]");
        return "The timeline for the user = [" + user + "]";
    }

    @RequestMapping("follow")
    String follow(@RequestParam String user, @RequestParam String followUser) {
        System.out.println("user = [" + user + "], followUser = [" + followUser + "]");
        return "The user = [" + user + "] wants to follow user ["+followUser+"]";
    }
}
