package com.github.jamie9504.board.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.jamie9504.board.web.dto.RoleRequest;
import com.github.jamie9504.board.web.dto.RoleResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
@Import(RoleService.class)
class RoleServiceTest {

    private static final String ROLE_NAME = "roleName";
    private static final String ROLE_EXPLANATION = "roleExplanation";
    private static final RoleRequest ROLE_REQUEST = new RoleRequest(ROLE_NAME, ROLE_EXPLANATION);
    private static final String ROLE_NEW_NAME = "newRoleName";
    private static final String ROLE_NEW_EXPLANATION = "newRoleExplanation";
    private static final RoleRequest ROLE_NEW_REQUEST
        = new RoleRequest(ROLE_NEW_NAME, ROLE_NEW_EXPLANATION);

    @Autowired
    private RoleService roleService;

    @DisplayName("롤 생성 - 성공")
    @Test
    void createRole_NewName_Success() {
        RoleResponse role = roleService.createRole(ROLE_REQUEST);

        assertThat(role.getId()).isNotNull();
        assertThat(role.getName()).isEqualTo(ROLE_NAME);
        assertThat(role.getExplanation()).isEqualTo(ROLE_EXPLANATION);
    }

    @DisplayName("롤 생성 - 예외 발생")
    @Test
    void createRole_ExistedName_ThrownException() {
        roleService.createRole(ROLE_REQUEST);

        assertThatThrownBy(() -> roleService.createRole(ROLE_REQUEST))
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void findAll() {
        List<RoleResponse> createdRoles = new ArrayList<>();
        createdRoles.add(roleService.createRole(ROLE_REQUEST));
        createdRoles.add(roleService.createRole(ROLE_NEW_REQUEST));

        List<RoleResponse> roles = roleService.findAll();
        assertThat(roles).containsAll(createdRoles);
    }

    @Test
    void findById() {
        RoleResponse role = roleService.createRole(ROLE_REQUEST);

        RoleResponse createdRole = roleService.findById(role.getId());

        assertThat(createdRole.getId()).isNotNull();
        assertThat(createdRole.getName()).isEqualTo(role.getName());
        assertThat(createdRole.getExplanation()).isEqualTo(role.getExplanation());
    }

    @Test
    void updateRole() {
        RoleResponse role = roleService.createRole(ROLE_REQUEST);

        roleService.updateRole(role.getId(), ROLE_NEW_REQUEST);
        RoleResponse updatedRole = roleService.findById(role.getId());

        assertThat(updatedRole.getId()).isEqualTo(role.getId());
        assertThat(updatedRole.getName()).isEqualTo(ROLE_NEW_REQUEST.getName());
        assertThat(updatedRole.getExplanation()).isEqualTo(ROLE_NEW_REQUEST.getExplanation());
    }

    @Test
    void deleteRole() {
        RoleResponse role = roleService.createRole(ROLE_REQUEST);

        roleService.deleteRole(role.getId());

        assertThatThrownBy(() -> roleService.findById(role.getId()))
            .isInstanceOf(IllegalArgumentException.class);
    }
}