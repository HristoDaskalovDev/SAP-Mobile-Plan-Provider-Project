package com.hristodaskalov.mobileplanprovider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    public MainController() {
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
