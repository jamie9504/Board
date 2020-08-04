package com.github.jamie9504.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("management")
public class ManagementPageController {

    @GetMapping("index")
    public String getIndex() {
        return "management/index";
    }
}
