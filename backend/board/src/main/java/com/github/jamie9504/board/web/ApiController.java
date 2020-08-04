package com.github.jamie9504.board.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public")
public class ApiController {

    @GetMapping("test1")
    public ResponseEntity<String> getTest1() {
        return ResponseEntity.ok("API Test 1");
    }

    @GetMapping("test2")
    public ResponseEntity<String> getTest2() {
        return ResponseEntity.ok("API Test 2");
    }
}

