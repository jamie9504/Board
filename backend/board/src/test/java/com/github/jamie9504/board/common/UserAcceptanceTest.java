package com.github.jamie9504.board.common;

import static com.github.jamie9504.board.user.UserConstants.TEST_ADMIN_EMAIL;
import static com.github.jamie9504.board.user.UserConstants.TEST_ADMIN_NICKNAME;
import static com.github.jamie9504.board.user.UserConstants.TEST_ADMIN_PASSWORD;
import static com.github.jamie9504.board.user.UserConstants.TEST_GUEST_EMAIL;
import static com.github.jamie9504.board.user.UserConstants.TEST_GUEST_NICKNAME;
import static com.github.jamie9504.board.user.UserConstants.TEST_GUEST_PASSWORD;
import static com.github.jamie9504.board.user.UserConstants.TEST_MEMBER_EMAIL;
import static com.github.jamie9504.board.user.UserConstants.TEST_MEMBER_NICKNAME;
import static com.github.jamie9504.board.user.UserConstants.TEST_MEMBER_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.jamie9504.board.user.model.UserRole;
import com.github.jamie9504.board.user.model.UserState;
import com.github.jamie9504.board.user.payload.UserForAdminResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class UserAcceptanceTest extends BaseAcceptanceTest {

    /**
     * Feature: Admin의 회원 관리
     * <p>
     * Scenario: Admin이 회원 관리를 한다.
     * <p>
     * Given Admin 회원 가입을 한다. 로그인을 한다. Token 받고, 해당 Token을 가지고 모든 작업을 진행한다.
     * <p>
     * When User 회원을 생성한다. Then User 회원이 생성된다.
     * <p>
     * When User 회원 정보를 조회한다. Then User 회원 정보가 조회된다.
     * <p>
     * When User 회원 정보를 수정한다. Then User 회원 정보가 수정된다.
     * <p>
     * When User 회원 정보를 삭제한다. Then User 회원 상태가 삭제 상태로 바뀐다.
     */
    @Test
    void manageUser_Admin() {
        createAdmin();
        String adminToken = login(TEST_ADMIN_EMAIL, TEST_ADMIN_PASSWORD);

        String location = createUser(adminToken, TEST_MEMBER_EMAIL, TEST_MEMBER_NICKNAME,
            TEST_MEMBER_PASSWORD, UserRole.MEMBER);

        UserForAdminResponse userResponseForAdmin
            = findWithToken(adminToken, location, UserForAdminResponse.class);
        assertThat(userResponseForAdmin.getId()).isNotNull();
        assertThat(userResponseForAdmin.getEmail()).isEqualTo(TEST_MEMBER_EMAIL);
        assertThat(userResponseForAdmin.getNickname()).isEqualTo(TEST_MEMBER_NICKNAME);
        assertThat(userResponseForAdmin.getRole()).isEqualTo(UserRole.MEMBER.name());
        assertThat(userResponseForAdmin.getState()).isEqualTo(UserState.REGISTERED.name());

        updateUser(adminToken, location, TEST_GUEST_EMAIL, TEST_GUEST_NICKNAME,
            TEST_GUEST_PASSWORD, UserRole.GUEST.name());

        userResponseForAdmin = findUser(adminToken, location);
        assertThat(userResponseForAdmin.getId()).isNotNull();
        assertThat(userResponseForAdmin.getEmail()).isEqualTo(TEST_GUEST_EMAIL);
        assertThat(userResponseForAdmin.getNickname()).isEqualTo(TEST_GUEST_NICKNAME);
        assertThat(userResponseForAdmin.getRole()).isEqualTo(UserRole.GUEST.name());
        assertThat(userResponseForAdmin.getState()).isEqualTo(UserState.REGISTERED.name());

        deleteUser(adminToken, location);

        assertThatThrownBy(() -> findUser(adminToken, location));
    }

    private void createAdmin() {
        Map<String, String> params = new HashMap<>();
        params.put("email", TEST_ADMIN_EMAIL);
        params.put("nickname", TEST_ADMIN_NICKNAME);
        params.put("password", TEST_ADMIN_PASSWORD);

        create(params, "/admin/sign-up");
    }

    private String createUser(String adminToken, String email, String nickname, String password,
        UserRole roleName) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("nickname", nickname);
        params.put("password", password);
        params.put("role", roleName.name());

        return createWithToken(adminToken, params, "/admin/users/");
    }

    private void updateUser(String adminToken, String location, String email, String nickname,
        String password, String roleName) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("nickname", nickname);
        params.put("password", password);
        params.put("role", roleName);

        updateWithToken(adminToken, params, location);
    }

    private void deleteUser(String adminToken, String location) {
        deleteWithToken(adminToken, location);
    }

    private UserForAdminResponse findUser(String adminToken, String location) {
        return findWithToken(adminToken, location, UserForAdminResponse.class);
    }
}
