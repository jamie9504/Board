package com.github.jamie9504.board.web.dto;

public class RoleRequest {

    private String roleName;

    private RoleRequest() {
    }

    public RoleRequest(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
