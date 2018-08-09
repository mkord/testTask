package com.mkord.twitt.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedList;

@RestController
public class MainController {

    @RequestMapping("/")
    String usage() {
        return "Usage text will be here";
    }

    @RequestMapping("post")
    String post(@RequestParam String user, @RequestParam String msg) {
        return "user = [" + user + "], msg = [" + msg + "]";
    }

    @RequestMapping("wall")
    Wall wall(@RequestParam String user) {
        Wall wall = new Wall(user);
        wall.addMessage("very old msg");
        wall.addMessage("old msg");
        wall.addMessage("fresh msg");
        wall.addMessage("the most fresh msg");
        return wall;
    }

    @RequestMapping("timeline")
    String timeline(@RequestParam String user) {
        return "The timeline for the user = [" + user + "]";
    }

    @RequestMapping("follow")
    String follow(@RequestParam String user, @RequestParam String followUser) {
        return "The user = [" + user + "] wants to follow user ["+followUser+"]";
    }

    private class Wall {
        String owner;
        Collection<String> messages;

        public Wall() {
            messages = new LinkedList<>();
        }

        Wall(String owner) {
            this.owner = owner;
            messages = new LinkedList<>();
        }

        public Wall(String owner, Collection<String> messages) {
            this.owner = owner;
            this.messages = messages;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        void addMessage(String msg) {
            messages.add(msg);
        }

        public String getOwner() {
            return owner;
        }

        public Collection<String> getMessages() {
            return messages;
        }
    }
}
