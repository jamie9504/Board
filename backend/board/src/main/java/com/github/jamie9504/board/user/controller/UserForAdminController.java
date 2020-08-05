package com.github.jamie9504.board.user.controller;

import static com.github.jamie9504.board.user.controller.UserForAdminController.ADMIN_URL_PATH;

import com.github.jamie9504.board.user.payload.UserForAdminRequest;
import com.github.jamie9504.board.user.payload.UserForAdminResponse;
import com.github.jamie9504.board.user.service.UserPrincipalDetailsService;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ADMIN_URL_PATH)
@AllArgsConstructor
public class UserForAdminController {

    protected static final String ADMIN_URL_PATH = "/admin";
    private static final String USER_URL_PATH = "/users";
    private static final String SIGN_UP_URL_PATH = "/sign-up";
    private static final String ADMIN_USER_URL_PATH = ADMIN_URL_PATH + USER_URL_PATH;

    private final UserPrincipalDetailsService userPrincipalDetailsService;

    @PostMapping(SIGN_UP_URL_PATH)
    public ResponseEntity<Void> createAdmin(@RequestBody UserForAdminRequest userForAdminRequest) {
        UserForAdminResponse admin = userPrincipalDetailsService.createAdmin(userForAdminRequest);
        String location = ADMIN_USER_URL_PATH + "/" + admin.getId();

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PostMapping(USER_URL_PATH)
    public ResponseEntity<Void> createUser(@RequestBody UserForAdminRequest userForAdminRequest) {
        UserForAdminResponse user
            = userPrincipalDetailsService.createUserForAdmin(userForAdminRequest);

        URI location = URI.create(ADMIN_USER_URL_PATH + "/" + user.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping(USER_URL_PATH + "/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id,
        @RequestBody UserForAdminRequest userForAdminRequest) {
        userPrincipalDetailsService.updateUserForAdmin(id, userForAdminRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(USER_URL_PATH + "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userPrincipalDetailsService.deleteForAdmin(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(USER_URL_PATH + "/{id}")
    public ResponseEntity<UserForAdminResponse> findUser(@PathVariable Long id) {
        UserForAdminResponse userForAdmin = userPrincipalDetailsService.findUserForAdmin(id);

        return ResponseEntity.ok(userForAdmin);
    }

    @GetMapping(USER_URL_PATH)
    public ResponseEntity<List<UserForAdminResponse>> findAllUser() {
        List<UserForAdminResponse> responses = userPrincipalDetailsService.findAllUserForAdmin();

        return ResponseEntity.ok(responses);
    }
}
