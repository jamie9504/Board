package com.github.jamie9504.board.user.payload;

import com.github.jamie9504.board.user.model.User;
import java.time.LocalDateTime;
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
public class AdminUserResponse {

    private Long id;
    private String email;
    private String nickname;
    private String role;
    private List<String> permissions;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static AdminUserResponse of(User user) {
        return AdminUserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .role(user.getRoleName())
            .permissions(user.getPermissionList())
            .state(user.getStateName())
            .createdAt(user.getCreatedAt())
            .lastModifiedAt(user.getModifiedAt())
            .build();
    }
}
