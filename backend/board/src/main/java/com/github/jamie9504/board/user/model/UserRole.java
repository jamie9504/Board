package com.github.jamie9504.board.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    MEMBER("ROLE_MEMBER"),
    GUEST("ROLE_GUEST");

    private final String name;
}
