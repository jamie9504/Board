package com.github.jamie9504.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("profile")
public class ProfilePageController {

    @GetMapping("index")
    public String getIndex() {
        return "profile/index";
    }
}
