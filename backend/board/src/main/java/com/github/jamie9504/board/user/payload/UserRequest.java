package com.github.jamie9504.board.user.payload;

import com.github.jamie9504.board.user.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class UserRequest {

    private String email;
    private String nickname;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public User toUser() {
        return User.builder()
            .email(email)
            .nickname(nickname)
            .password(password)
            .build();
    }
}
