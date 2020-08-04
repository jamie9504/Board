package com.github.jamie9504.board.user.payload;

import com.github.jamie9504.board.user.model.User;
import java.util.List;
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
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private String role;
    private List<String> permissions;

    public static UserResponse of(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .role(user.getRoleName())
            .permissions(user.getPermissionList())
            .build();
    }
}
