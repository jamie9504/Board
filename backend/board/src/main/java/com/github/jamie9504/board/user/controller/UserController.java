package com.github.jamie9504.board.user.controller;

import com.github.jamie9504.board.user.payload.UserRequest;
import com.github.jamie9504.board.user.payload.UserResponse;
import com.github.jamie9504.board.user.service.UserPrincipalDetailsService;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    protected static final String SIGN_UP_URL_PATH = "/sign-up";
    private static final String USERS_URL_PATH = "/users";
    private final UserPrincipalDetailsService userPrincipalDetailsService;

    @PostMapping(USERS_URL_PATH + SIGN_UP_URL_PATH)
    public ResponseEntity<Void> signUp(UserRequest userRequest) {
        UserResponse user = userPrincipalDetailsService.createUser(userRequest);

        URI location = URI.create(USERS_URL_PATH + "/" + user.getId());
        return ResponseEntity.created(location).build();
    }
}
