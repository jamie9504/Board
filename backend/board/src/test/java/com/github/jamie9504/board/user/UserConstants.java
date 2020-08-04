package com.github.jamie9504.board.user;

import com.github.jamie9504.board.user.payload.UserRequest;

public class UserConstants {

    public static final String TEST_USER_EMAIL = "test-user@test.com";
    public static final String TEST_USER_NICKNAME = "test-user";
    public static final String TEST_USER_PASSWORD = "test-user-password";

    public static final String TEST_GUEST_EMAIL = "test-guest@test.com";
    public static final String TEST_GUEST_NICKNAME = "test-guest";
    public static final String TEST_GUEST_PASSWORD = "test-guest-password";
    public static final UserRequest TEST_GUEST_REQUEST
        = new UserRequest(TEST_GUEST_EMAIL, TEST_GUEST_NICKNAME, TEST_GUEST_PASSWORD);
}
