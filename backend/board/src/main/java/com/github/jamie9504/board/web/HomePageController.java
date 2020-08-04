package com.github.jamie9504.board.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomePageController {

    Logger logger = LoggerFactory.getLogger(HomePageController.class);

    @GetMapping(value = "/home")
    public String getHome() {
        return "home";
    }

    @GetMapping("index")
    public String getIndex() {
        logger.info("=========> index 진입 =======>");
        return "index";
    }
}
