package com.github.jamie9504.board.user.payload;

import com.github.jamie9504.board.user.model.User;
import com.github.jamie9504.board.user.model.UserRole;
import com.github.jamie9504.board.user.model.UserState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class UserForAdminRequest {

    private String email;
    private String nickname;
    private String password;
    private String role;

    @Builder.Default
    private String state = UserState.REGISTERED.name();

    @Builder.Default
    private String permissions = "";

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User toUser() {
        return User.builder()
            .email(email)
            .nickname(nickname)
            .password(password)
            .role(UserRole.valueOf(role))
            .state(UserState.valueOf(state))
            .permissions(permissions)
            .build();
    }
}
