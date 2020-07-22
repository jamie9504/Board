package com.github.jamie9504.board.web.dto;

public class RoleResponse {

    private Long id;
    private String roleName;

    public RoleResponse(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return "RoleResponse{" +
            "id=" + id +
            ", roleName='" + roleName + '\'' +
            '}';
    }
}
