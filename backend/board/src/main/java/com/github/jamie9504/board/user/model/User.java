package com.github.jamie9504.board.user.model;

import com.github.jamie9504.board.common.BaseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User extends BaseEntity {

    private static final String SPLIT_DELIMITER = ",";

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    private int used = 1;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.GUEST;

    @Builder.Default
    private String permissions = "";

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private UserState state = UserState.REGISTERED;

    public void update(User user) {
        if (Objects.nonNull(user.email)) {
            this.email = user.email;
        }
        if (Objects.nonNull(user.nickname)) {
            this.nickname = user.nickname;
        }
        if (Objects.nonNull(user.password)) {
            this.password = user.password;
        }
        if (Objects.nonNull(user.role)) {
            this.role = user.role;
        }
        if (Objects.nonNull(user.permissions)) {
            this.permissions = user.permissions;
        }
        if (Objects.nonNull(user.state)) {
            this.state = user.state;
        }
    }

    public boolean isNotSameEmail(String email) {
        return !this.email.equals(email);
    }

    public List<String> getPermissionList() {
        return getSplitList(this.permissions);
    }

    private List<String> getSplitList(String value) {
        if (value.length() > 0) {
            return Arrays.asList(value.split(SPLIT_DELIMITER));
        }
        return new ArrayList<>();
    }

    public String getRoleNameWithPrefix() {
        return this.role.getName();
    }

    public String getRoleName() {
        return this.role.name();
    }

    public String getStateName() {
        return this.state.name();
    }
}
