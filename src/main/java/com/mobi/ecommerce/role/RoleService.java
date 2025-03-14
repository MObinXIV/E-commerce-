package com.mobi.ecommerce.role;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(RoleType roleType) {
        return roleRepository.findByName(roleType)
                .orElseThrow(() -> new RuntimeException("Role " + roleType + " not found!"));
    }
}
