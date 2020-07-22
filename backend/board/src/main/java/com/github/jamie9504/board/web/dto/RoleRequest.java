package com.github.jamie9504.board.web.dto;

import com.github.jamie9504.board.entity.Role;

public class RoleRequest {

    private String name;
    private String explanation;

    private RoleRequest() {
    }

    public RoleRequest(String name, String explanation) {
        this.name = name;
        this.explanation = explanation;
    }

    public Role toRole() {
        return new Role(name, explanation);
    }

    public String getName() {
        return name;
    }

    public String getExplanation() {
        return explanation;
    }

    @Override
    public String toString() {
        return "RoleRequest{" +
            "name='" + name + '\'' +
            ", explanation='" + explanation + '\'' +
            '}';
    }
}
