package com.github.jamie9504.board.user.controller;

import static com.github.jamie9504.board.user.controller.AdminUserController.ADMIN_URL_PATH;

import com.github.jamie9504.board.user.model.UserState;
import com.github.jamie9504.board.user.payload.AdminUserResponse;
import com.github.jamie9504.board.user.payload.UserForAdminRequest;
import com.github.jamie9504.board.user.service.UserPrincipalDetailsService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class AdminUserController {

    protected static final String ADMIN_URL_PATH = "/admin";
    private static final String USER_URL_PATH = "/users";
    private static final String SIGN_UP_URL_PATH = "/sign-up";
    private static final String ADMIN_USER_URL_PATH = ADMIN_URL_PATH + USER_URL_PATH;
    private final UserPrincipalDetailsService userPrincipalDetailsService;
    private final Map<Long, AdminUserResponse> adminUserResponses = new HashMap<>();

    @PostMapping(SIGN_UP_URL_PATH)
    public ResponseEntity<Void> createAdmin(@RequestBody UserForAdminRequest userForAdminRequest) {
        AdminUserResponse admin = userPrincipalDetailsService.createAdmin(userForAdminRequest);
        String location = ADMIN_USER_URL_PATH + "/" + admin.getId();

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PostMapping(USER_URL_PATH)
    public ResponseEntity<Void> createUser(@RequestBody UserForAdminRequest userForAdminRequest) {
        AdminUserResponse user = userPrincipalDetailsService
            .createUserForAdmin(userForAdminRequest);
        Long id = adminUserResponses.size() + 1L;
        adminUserResponses.put(id, AdminUserResponse.builder()
            .id(id)
            .nickname(userForAdminRequest.getNickname())
            .email(userForAdminRequest.getEmail())
            .role(userForAdminRequest.getRole())
            .state(UserState.REGISTERED.name())
            .createdAt(LocalDateTime.now())
            .lastModifiedAt(LocalDateTime.now())
            .build());

        return ResponseEntity.created(URI.create(ADMIN_USER_URL_PATH + "/" + id)).build();
    }

    @PutMapping(USER_URL_PATH + "/{id}")
    public ResponseEntity<AdminUserResponse> updateUser(@PathVariable Long id,
        @RequestBody UserForAdminRequest userForAdminRequest) {
        adminUserResponses.put(id, AdminUserResponse.builder()
            .id(id)
            .nickname(userForAdminRequest.getNickname())
            .email(userForAdminRequest.getEmail())
            .role(userForAdminRequest.getRole())
            .state(UserState.REGISTERED.name())
            .createdAt(LocalDateTime.now())
            .lastModifiedAt(LocalDateTime.now())
            .build());

        AdminUserResponse adminUserResponse = adminUserResponses.get(id);
        return ResponseEntity.ok().body(adminUserResponse);
    }

    @DeleteMapping(USER_URL_PATH + "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        AdminUserResponse adminUserResponse = adminUserResponses.remove(id);
        adminUserResponses.put(id, AdminUserResponse.builder()
            .id(id)
            .nickname(adminUserResponse.getNickname())
            .email(adminUserResponse.getEmail())
            .role(adminUserResponse.getRole())
            .state(UserState.DELETED.name())
            .createdAt(adminUserResponse.getCreatedAt())
            .lastModifiedAt(LocalDateTime.now())
            .build());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(USER_URL_PATH + "/{id}")
    public ResponseEntity<AdminUserResponse> findUser(@PathVariable Long id) {
        AdminUserResponse adminUserResponse = adminUserResponses.get(id);
        return ResponseEntity.ok(adminUserResponse);
    }

    @GetMapping(USER_URL_PATH)
    public ResponseEntity<List<AdminUserResponse>> findAllUser() {
        ArrayList<AdminUserResponse> responses
            = new ArrayList<>(adminUserResponses.values());
        return ResponseEntity.ok(responses);
    }
}
