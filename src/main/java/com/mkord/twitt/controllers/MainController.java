package com.mkord.twitt.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/")
    String usage() {
        return "Usage text will be here";
    }
}
