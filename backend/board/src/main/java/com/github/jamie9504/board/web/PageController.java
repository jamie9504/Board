package com.github.jamie9504.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping(value = "/home")
    public String getHome() {
        return "home";
    }
}
