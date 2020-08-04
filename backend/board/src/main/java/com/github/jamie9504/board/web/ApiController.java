package com.github.jamie9504.board.web;

import com.github.jamie9504.board.user.User;
import com.github.jamie9504.board.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public")
@RequiredArgsConstructor
public class ApiController {

    private final UserRepository userRepository;

    @GetMapping("test1")
    public ResponseEntity<String> getTest1() {
        return ResponseEntity.ok("API Test 1");
    }

    @GetMapping("test2")
    public ResponseEntity<String> getTest2() {
        return ResponseEntity.ok("API Test 2");
    }

    @GetMapping("users")
    public List<User> allUsers() {
        return this.userRepository.findAll();
    }
}
