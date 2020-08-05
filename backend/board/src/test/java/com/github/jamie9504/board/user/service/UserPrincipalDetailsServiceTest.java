package com.github.jamie9504.board.user.service;

import static com.github.jamie9504.board.user.UserConstants.TEST_GUEST_NICKNAME;
import static com.github.jamie9504.board.user.UserConstants.TEST_GUEST_PASSWORD;
import static com.github.jamie9504.board.user.UserConstants.TEST_MEMBER_EMAIL;
import static com.github.jamie9504.board.user.UserConstants.TEST_MEMBER_NICKNAME;
import static com.github.jamie9504.board.user.UserConstants.TEST_MEMBER_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.jamie9504.board.user.exception.NonexistentUser;
import com.github.jamie9504.board.user.payload.UserRequest;
import com.github.jamie9504.board.user.payload.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;

@DataJpaTest
@Import({UserPrincipalDetailsService.class})
class UserPrincipalDetailsServiceTest {

    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService;

    private UserRequest testUserRequest;

    @BeforeEach
    void setUp() {
        testUserRequest = new UserRequest(TEST_MEMBER_EMAIL, TEST_MEMBER_NICKNAME,
            TEST_MEMBER_PASSWORD);
    }

    @DisplayName("사용자 생성")
    @Test
    void createUser_Success() {
        UserResponse user = userPrincipalDetailsService.createUser(testUserRequest);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo(testUserRequest.getEmail());
        assertThat(user.getNickname()).isEqualTo(testUserRequest.getNickname());
        assertThat(user.getRole()).isNotNull();
        assertThat(user.getPermissions()).isNotNull();
    }

    @DisplayName("사용자 생성 - 동일한 이메일이 있는 경우, 예외")
    @Test
    void createUser_SameEmail_ThrownException() {
        userPrincipalDetailsService.createUser(testUserRequest);
        UserRequest userRequest
            = new UserRequest(TEST_MEMBER_EMAIL, TEST_GUEST_NICKNAME, TEST_GUEST_PASSWORD);

        assertThatThrownBy(() -> userPrincipalDetailsService.createUser(userRequest));
    }

    @DisplayName("사용자 불러오기 - 이메일이 있는 경우, 성공")
    @Test
    void loadUserByUsername_hasEmail_Success() {
        UserResponse user = userPrincipalDetailsService.createUser(testUserRequest);

        UserDetails userDetails = userPrincipalDetailsService
            .loadUserByUsername(testUserRequest.getEmail());

        assertThat(userDetails.getUsername()).isEqualTo(user.getEmail());
        assertThat(userDetails.getPassword()).isNotNull();
        assertThat(userDetails.getAuthorities()).isNotNull();
    }

    @DisplayName("사용자 불러오기 - 이메일이 없는 경우, 예외")
    @Test
    void loadUserByUsername_hasNotEmail_ThrownException() {
        assertThatThrownBy(() -> userPrincipalDetailsService.loadUserByUsername(TEST_MEMBER_EMAIL))
            .isInstanceOf(NonexistentUser.class);
    }
}