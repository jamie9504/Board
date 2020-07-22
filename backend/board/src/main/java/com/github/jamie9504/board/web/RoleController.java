package com.github.jamie9504.board.web;

import com.github.jamie9504.board.service.RoleService;
import com.github.jamie9504.board.web.dto.RoleRequest;
import com.github.jamie9504.board.web.dto.RoleResponse;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private static final String ROLES_URL_PREFIX = "/roles/";
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> findAllRole() {
        List<RoleResponse> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findRole(@PathVariable Long id) {
        RoleResponse role = roleService.findById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse role = roleService.createRole(roleRequest);
        String location = ROLES_URL_PREFIX + role.getId();
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRole(@PathVariable("id") Long id,
        @RequestBody RoleRequest roleRequest) {
        roleService.updateRole(id, roleRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
