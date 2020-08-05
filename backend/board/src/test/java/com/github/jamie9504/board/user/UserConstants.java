package com.github.jamie9504.board.user;

import com.github.jamie9504.board.user.model.UserRole;
import com.github.jamie9504.board.user.payload.UserForAdminRequest;
import com.github.jamie9504.board.user.payload.UserRequest;

public class UserConstants {

    public static final String TEST_ADMIN_EMAIL = "test-admin@test.com";
    public static final String TEST_ADMIN_NICKNAME = "test-admin";
    public static final String TEST_ADMIN_PASSWORD = "test-admin-password";
    public static final UserForAdminRequest TEST_ADMIN_USER_FOR_ADMIN_REQUEST
        = new UserForAdminRequest(TEST_ADMIN_EMAIL, TEST_ADMIN_NICKNAME, TEST_ADMIN_PASSWORD,
        UserRole.ADMIN.name());
    public static final UserRequest TEST_ADMIN_USER_REQUEST
        = new UserRequest(TEST_ADMIN_EMAIL, TEST_ADMIN_NICKNAME, TEST_ADMIN_PASSWORD);

    public static final String TEST_MANAGER_EMAIL = "test-manager@test.com";
    public static final String TEST_MANAGER_NICKNAME = "test-manager";
    public static final String TEST_MANAGER_PASSWORD = "test-manager-password";
    public static final UserForAdminRequest TEST_MANAGER_USER_FOR_ADMIN_REQUEST
        = new UserForAdminRequest(TEST_MANAGER_EMAIL, TEST_MANAGER_NICKNAME, TEST_MANAGER_PASSWORD,
        UserRole.MANAGER.name());
    public static final UserRequest TEST_MANAGER_USER_REQUEST
        = new UserRequest(TEST_MANAGER_EMAIL, TEST_MANAGER_NICKNAME, TEST_MANAGER_PASSWORD);

    public static final String TEST_MEMBER_EMAIL = "test-member@test.com";
    public static final String TEST_MEMBER_NICKNAME = "test-member";
    public static final String TEST_MEMBER_PASSWORD = "test-member-password";
    public static final UserForAdminRequest TEST_MEMBER_USER_FOR_ADMIN_REQUEST
        = new UserForAdminRequest(TEST_MEMBER_EMAIL, TEST_MEMBER_NICKNAME, TEST_MEMBER_PASSWORD,
        UserRole.MEMBER.name());
    public static final UserRequest TEST_MEMBER_USER_REQUEST
        = new UserRequest(TEST_MEMBER_EMAIL, TEST_MEMBER_NICKNAME, TEST_MEMBER_PASSWORD);

    public static final String TEST_GUEST_EMAIL = "test-guest@test.com";
    public static final String TEST_GUEST_NICKNAME = "test-guest";
    public static final String TEST_GUEST_PASSWORD = "test-guest-password";
    public static final UserForAdminRequest TEST_GUEST_USER_FOR_ADMIN_REQUEST
        = new UserForAdminRequest(TEST_GUEST_EMAIL, TEST_GUEST_NICKNAME, TEST_GUEST_PASSWORD,
        UserRole.GUEST.name());
    public static final UserRequest TEST_GUEST_USER_REQUEST
        = new UserRequest(TEST_GUEST_EMAIL, TEST_GUEST_NICKNAME, TEST_GUEST_PASSWORD);
}
