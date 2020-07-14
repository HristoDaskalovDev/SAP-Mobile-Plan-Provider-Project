package com.hristodaskalov.mobileplanprovider.controller;

import com.hristodaskalov.mobileplanprovider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = userService.
        return "index";
    }
}
