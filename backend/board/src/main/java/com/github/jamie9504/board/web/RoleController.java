package com.github.jamie9504.board.web;

import com.github.jamie9504.board.web.dto.RoleRequest;
import com.github.jamie9504.board.web.dto.RoleResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    public RoleController() {
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> findAllRole() {
        List<RoleResponse> roles = new ArrayList<>();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findRole(@PathVariable String id) {
        RoleResponse role = new RoleResponse(1L, "");
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody RoleRequest roleRequest) {
        String location = "";
        return ResponseEntity.created(URI.create(location)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        return ResponseEntity.noContent().build();
    }
}
