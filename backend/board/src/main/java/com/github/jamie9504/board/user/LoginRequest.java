package com.github.jamie9504.board.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class LoginRequest {

    private String email;
    private String password;
}
