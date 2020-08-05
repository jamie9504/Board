package com.github.jamie9504.board.user.service;

import static com.github.jamie9504.board.user.UserConstants.TEST_ADMIN_EMAIL;
import static com.github.jamie9504.board.user.UserConstants.TEST_ADMIN_USER_FOR_ADMIN_REQUEST;
import static com.github.jamie9504.board.user.UserConstants.TEST_GUEST_NICKNAME;
import static com.github.jamie9504.board.user.UserConstants.TEST_GUEST_PASSWORD;
import static com.github.jamie9504.board.user.UserConstants.TEST_GUEST_USER_FOR_ADMIN_REQUEST;
import static com.github.jamie9504.board.user.UserConstants.TEST_MANAGER_USER_FOR_ADMIN_REQUEST;
import static com.github.jamie9504.board.user.UserConstants.TEST_MEMBER_EMAIL;
import static com.github.jamie9504.board.user.UserConstants.TEST_MEMBER_NICKNAME;
import static com.github.jamie9504.board.user.UserConstants.TEST_MEMBER_PASSWORD;
import static com.github.jamie9504.board.user.UserConstants.TEST_MEMBER_USER_FOR_ADMIN_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.jamie9504.board.user.exception.AlreadyExistsEntityException;
import com.github.jamie9504.board.user.exception.NonexistentUser;
import com.github.jamie9504.board.user.model.UserRole;
import com.github.jamie9504.board.user.model.UserState;
import com.github.jamie9504.board.user.payload.UserForAdminRequest;
import com.github.jamie9504.board.user.payload.UserForAdminResponse;
import com.github.jamie9504.board.user.payload.UserRequest;
import com.github.jamie9504.board.user.payload.UserResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;

@DataJpaTest
@Import(UserPrincipalDetailsService.class)
class UserPrincipalDetailsServiceTest {

    protected static final long INVALID_ID = -1L;
    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService;

    private UserRequest testUserRequest;

    @BeforeEach
    void setUp() {
        testUserRequest = new UserRequest(TEST_MEMBER_EMAIL, TEST_MEMBER_NICKNAME,
            TEST_MEMBER_PASSWORD);
    }

    @DisplayName("어드민 생성")
    @Test
    void createAdmin_Success() {
        UserForAdminResponse admin = userPrincipalDetailsService.createAdmin(
            TEST_ADMIN_USER_FOR_ADMIN_REQUEST);

        assertThat(admin.getId()).isNotNull();
        assertThat(admin.getEmail()).isEqualTo(TEST_ADMIN_USER_FOR_ADMIN_REQUEST.getEmail());
        assertThat(admin.getNickname()).isEqualTo(TEST_ADMIN_USER_FOR_ADMIN_REQUEST.getNickname());
        assertThat(admin.getRole()).isEqualTo(UserRole.ADMIN.name());
        assertThat(admin.getPermissions()).isNotNull();
        assertThat(admin.getState()).isEqualTo(UserState.REGISTERED.name());
    }

    @DisplayName("어드민 생성 - 예외 발생, 동일한 이메일")
    @Test
    void createAdmin_SameEmail_ThrownException() {
        userPrincipalDetailsService.createAdmin(TEST_ADMIN_USER_FOR_ADMIN_REQUEST);
        UserForAdminRequest userForAdminRequest = UserForAdminRequest.builder()
            .email(TEST_ADMIN_EMAIL)
            .nickname(TEST_GUEST_NICKNAME)
            .password(TEST_GUEST_PASSWORD)
            .role(UserRole.ADMIN.name())
            .build();

        assertThatThrownBy(() -> userPrincipalDetailsService.createAdmin(userForAdminRequest))
            .isInstanceOf(AlreadyExistsEntityException.class);
    }

    @DisplayName("관리자가 사용자 생성")
    @Test
    void createUserForAdmin_Success() {
        UserForAdminResponse admin = userPrincipalDetailsService.createUserForAdmin(
            TEST_ADMIN_USER_FOR_ADMIN_REQUEST);

        assertThat(admin.getId()).isNotNull();
        assertThat(admin.getEmail()).isEqualTo(TEST_ADMIN_USER_FOR_ADMIN_REQUEST.getEmail());
        assertThat(admin.getNickname()).isEqualTo(TEST_ADMIN_USER_FOR_ADMIN_REQUEST.getNickname());
        assertThat(admin.getRole()).isEqualTo(UserRole.ADMIN.name());
        assertThat(admin.getPermissions()).isNotNull();
        assertThat(admin.getState()).isEqualTo(UserState.REGISTERED.name());
    }

    @DisplayName("관리자가 사용자 생성 - 예외 발생, 동일한 이메일")
    @Test
    void createUserForAdmin_SameEmail_ThrownException() {
        userPrincipalDetailsService.createAdmin(TEST_ADMIN_USER_FOR_ADMIN_REQUEST);
        UserForAdminRequest userForAdminRequest = UserForAdminRequest.builder()
            .email(TEST_ADMIN_EMAIL)
            .nickname(TEST_GUEST_NICKNAME)
            .password(TEST_GUEST_PASSWORD)
            .role(UserRole.MANAGER.name())
            .build();

        assertThatThrownBy(() -> userPrincipalDetailsService.createAdmin(userForAdminRequest))
            .isInstanceOf(AlreadyExistsEntityException.class);
    }

    @DisplayName("사용자 회원 가입")
    @Test
    void createUser_Success() {
        UserResponse user = userPrincipalDetailsService.createUser(testUserRequest);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo(testUserRequest.getEmail());
        assertThat(user.getNickname()).isEqualTo(testUserRequest.getNickname());
        assertThat(user.getRole()).isNotNull();
        assertThat(user.getPermissions()).isNotNull();
    }

    @DisplayName("사용자 회원 가입 - 예외 발생, 동일한 이메일")
    @Test
    void createUser_SameEmail_ThrownException() {
        userPrincipalDetailsService.createUser(testUserRequest);
        UserRequest userRequest
            = new UserRequest(TEST_MEMBER_EMAIL, TEST_GUEST_NICKNAME, TEST_GUEST_PASSWORD);

        assertThatThrownBy(() -> userPrincipalDetailsService.createUser(userRequest))
            .isInstanceOf(AlreadyExistsEntityException.class);
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

    @DisplayName("사용자 정보 수정 - 성공")
    @Test
    void updateUserForAdmin_Success() {
        UserForAdminResponse manager
            = userPrincipalDetailsService.createUserForAdmin(TEST_MANAGER_USER_FOR_ADMIN_REQUEST);

        userPrincipalDetailsService
            .updateUserForAdmin(manager.getId(), TEST_MEMBER_USER_FOR_ADMIN_REQUEST);

        UserForAdminResponse updatedManager
            = userPrincipalDetailsService.findUserForAdmin(manager.getId());
        assertThat(updatedManager.getId()).isEqualTo(manager.getId());
        assertThat(updatedManager.getEmail())
            .isEqualTo(TEST_MEMBER_USER_FOR_ADMIN_REQUEST.getEmail());
        assertThat(updatedManager.getNickname())
            .isEqualTo(TEST_MEMBER_USER_FOR_ADMIN_REQUEST.getNickname());
        assertThat(updatedManager.getRole())
            .isEqualTo(TEST_MEMBER_USER_FOR_ADMIN_REQUEST.getRole());
        assertThat(updatedManager.getState())
            .isEqualTo(TEST_MEMBER_USER_FOR_ADMIN_REQUEST.getState());
        assertThat(String.join(",", updatedManager.getPermissions()))
            .isEqualTo(TEST_MEMBER_USER_FOR_ADMIN_REQUEST.getPermissions());
    }

    @DisplayName("사용자 정보 수정 - 예외 발생, ID가 없음")
    @Test
    void updateUserForAdmin_hasNoId_ThrownException() {
        assertThatThrownBy(() -> userPrincipalDetailsService
            .updateUserForAdmin(INVALID_ID, TEST_MEMBER_USER_FOR_ADMIN_REQUEST))
            .isInstanceOf(NonexistentUser.class);
    }

    @DisplayName("사용자 정보 수정 - 예외 발생, 다른 사용자와 Email 겹침")
    @Test
    void updateUserForAdmin_DuplicateEmail_ThrownException() {
        UserForAdminResponse manager
            = userPrincipalDetailsService.createUserForAdmin(TEST_MANAGER_USER_FOR_ADMIN_REQUEST);
        userPrincipalDetailsService.createUserForAdmin(TEST_MEMBER_USER_FOR_ADMIN_REQUEST);

        assertThatThrownBy(() -> userPrincipalDetailsService
            .updateUserForAdmin(manager.getId(), TEST_MEMBER_USER_FOR_ADMIN_REQUEST))
            .isInstanceOf(AlreadyExistsEntityException.class);
    }

    @DisplayName("사용자 정보 삭제 - 성공")
    @Test
    void deleteForAdmin_Success() {
        UserForAdminResponse createdUser
            = userPrincipalDetailsService.createUserForAdmin(TEST_MANAGER_USER_FOR_ADMIN_REQUEST);

        Long id = createdUser.getId();
        userPrincipalDetailsService.deleteForAdmin(id);
        assertThatThrownBy(() -> userPrincipalDetailsService.findUserForAdmin(id))
            .isInstanceOf(NonexistentUser.class);
    }

    @DisplayName("사용자 정보 삭제 - 예외 발생, 아이디가 없음")
    @Test
    void deleteForAdmin_HasNoId_ThrownException() {
        assertThatThrownBy(() -> userPrincipalDetailsService.deleteForAdmin(INVALID_ID))
            .isInstanceOf(NonexistentUser.class);
    }

    @DisplayName("사용자 정보 조회 - 성공")
    @Test
    void findUserForAdmin_Success() {
        UserForAdminResponse createdUser
            = userPrincipalDetailsService.createUserForAdmin(TEST_MANAGER_USER_FOR_ADMIN_REQUEST);

        UserForAdminResponse foundUser
            = userPrincipalDetailsService.findUserForAdmin(createdUser.getId());

        assertThat(createdUser).isEqualTo(foundUser);
    }

    @DisplayName("사용자 정보 조회 - 예외 발생, 아이디가 없음")
    @Test
    void findUserForAdmin_HasNoId_ThrownException() {
        assertThatThrownBy(() -> userPrincipalDetailsService.findUserForAdmin(INVALID_ID))
            .isInstanceOf(NonexistentUser.class);
    }

    @DisplayName("사용자 정보 전체 조회 - 성공")
    @Test
    void findAllUserForAdmin() {
        UserForAdminResponse manager
            = userPrincipalDetailsService.createUserForAdmin(TEST_MANAGER_USER_FOR_ADMIN_REQUEST);
        UserForAdminResponse guest
            = userPrincipalDetailsService.createUserForAdmin(TEST_GUEST_USER_FOR_ADMIN_REQUEST);

        List<UserForAdminResponse> allUsers = userPrincipalDetailsService.findAllUserForAdmin();

        assertThat(allUsers).contains(manager);
        assertThat(allUsers).contains(guest);
    }
}