package com.github.jamie9504.board.user;

import com.github.jamie9504.board.user.payload.UserRequest;

public class UserConstants {

    public static final String TEST_ADMIN_EMAIL = "test-admin@test.com";
    public static final String TEST_ADMIN_NICKNAME = "test-admin";
    public static final String TEST_ADMIN_PASSWORD = "test-admin-password";

    public static final String TEST_MEMBER_EMAIL = "test-member@test.com";
    public static final String TEST_MEMBER_NICKNAME = "test-member";
    public static final String TEST_MEMBER_PASSWORD = "test-member-password";

    public static final String TEST_GUEST_EMAIL = "test-guest@test.com";
    public static final String TEST_GUEST_NICKNAME = "test-guest";
    public static final String TEST_GUEST_PASSWORD = "test-guest-password";
    public static final UserRequest TEST_GUEST_REQUEST
        = new UserRequest(TEST_GUEST_EMAIL, TEST_GUEST_NICKNAME, TEST_GUEST_PASSWORD);
}
