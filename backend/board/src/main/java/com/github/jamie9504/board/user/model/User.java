package com.github.jamie9504.board.user.model;

import com.github.jamie9504.board.common.BaseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    private int used;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.GUEST;

    @Builder.Default
    private String permissions = "";

    public List<String> getPermissionList() {
        return getSplitList(this.permissions);
    }

    private List<String> getSplitList(String value) {
        if (value.length() > 0) {
            return Arrays.asList(value.split(SPLIT_DELIMITER));
        }
        return new ArrayList<>();
    }

    public String getRoleName() {
        return this.role.getName();
    }
}
