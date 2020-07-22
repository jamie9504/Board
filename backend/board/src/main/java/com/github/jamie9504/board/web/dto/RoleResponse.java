package com.github.jamie9504.board.web.dto;

import com.github.jamie9504.board.entity.Role;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RoleResponse {

    private Long id;
    private String name;
    private String explanation;

    private RoleResponse() {
    }

    public RoleResponse(Long id, String name, String explanation) {
        this.id = id;
        this.name = name;
        this.explanation = explanation;
    }

    public static RoleResponse of(Role role) {
        return new RoleResponse(role.getId(), role.getName(), role.getExplanation());
    }

    public static List<RoleResponse> listOf(List<Role> roles) {
        return roles.stream()
            .map(RoleResponse::of)
            .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExplanation() {
        return explanation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleResponse that = (RoleResponse) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(explanation, that.explanation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, explanation);
    }

    @Override
    public String toString() {
        return "RoleResponse{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", explanation='" + explanation + '\'' +
            '}';
    }
}
