package com.github.jamie9504.board.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
public class HomePageController {

    @GetMapping(value = "/home")
    public String getHome() {
        return "home";
    }

    @GetMapping("index")
    public String getIndex() {
        log.info("=========> index 진입 =======>");
        return "index";
    }
}
