package com.github.jamie9504.board.service;

import com.github.jamie9504.board.entity.Role;
import com.github.jamie9504.board.entity.RoleRepository;
import com.github.jamie9504.board.web.dto.RoleRequest;
import com.github.jamie9504.board.web.dto.RoleResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleResponse> findAll() {
        List<Role> roles = roleRepository.findAll();
        return RoleResponse.listOf(roles);
    }

    public RoleResponse findById(Long id) {
        Role role = findRoleById(id);
        return RoleResponse.of(role);
    }

    private Role findRoleById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 ID가 없습니다."));
    }

    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = roleRequest.toRole();
        Role createdRole = roleRepository.save(role);
        return RoleResponse.of(createdRole);
    }

    public void updateRole(Long id, RoleRequest roleRequest) {
        Role role = findRoleById(id);
        role.update(roleRequest.toRole());
    }

    public void deleteRole(Long id) {
        Role role = findRoleById(id);
        roleRepository.delete(role);
    }
}
