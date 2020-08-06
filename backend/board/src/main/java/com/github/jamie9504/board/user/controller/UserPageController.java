package com.github.jamie9504.board.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping("/users/sign-up")
    public String signUp() {
        return "/user/sign-up";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "/user/login";
    }
}
