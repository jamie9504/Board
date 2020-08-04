package com.github.jamie9504.board.web;

import com.github.jamie9504.board.user.User;
import com.github.jamie9504.board.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public")
@RequiredArgsConstructor
@CrossOrigin
public class ApiController {

    private final UserRepository userRepository;

    // Available to all authenticated users
    @GetMapping("test")
    public ResponseEntity<String> getTest1() {
        return ResponseEntity.ok("API Test 1");
    }

    // Available to managers
    @GetMapping("management/reports")
    public ResponseEntity<String> getTest2() {
        return ResponseEntity.ok("API Test 2");
    }

    // Available to ROLE_ADMIN
    @GetMapping("admin/users")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(this.userRepository.findAll());
    }
}
