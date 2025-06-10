package com.usermanagemet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagemet.domain.Role;
import com.usermanagemet.repositories.RoleRepository;
import com.usermanagemet.userDTOs.RoleDto;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> addRole(@RequestBody RoleDto roleDto) {
        if (roleDto.getName() == null || roleDto.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Role name cannot be empty");
        }

        // Convert to standard format: ROLE_PRODUCT_MANAGER
        String roleName = "ROLE_" + roleDto.getName().trim().toUpperCase();

        if (roleRepository.existsByName(roleName)) {
            return ResponseEntity.badRequest().body("Role already exists: " + roleName);
        }

        
        Role newRole = new Role();
        newRole.setName(roleName);
        roleRepository.save(newRole);

        return ResponseEntity.ok("Role created is : " + roleName);
    }
}
