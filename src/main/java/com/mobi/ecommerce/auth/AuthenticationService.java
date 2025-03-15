package com.mobi.ecommerce.auth;

import com.mobi.ecommerce.role.RoleRepository;
import com.mobi.ecommerce.role.RoleType;
import com.mobi.ecommerce.role.User_Role;
import com.mobi.ecommerce.security.JwtService;
import com.mobi.ecommerce.user.User;
import com.mobi.ecommerce.user.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
//@Tag(name = "Authentication")
public class AuthenticationService {
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }
    @Transactional
    public AuthenticationResponse register(RegistrationRequest request) {
        var roleOptional = roleRepository.findByName(RoleType.CUSTOMER);

        if (roleOptional.isEmpty()) {
            throw new RuntimeException("Default role CUSTOMER not found!");
        }

        var role = roleOptional.get(); // Extract the Role
        System.out.println("✅ Role found: " + role.getName());
        var user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAccountLocked(false);
        user.setAccountEnabled(false);

        // Create and assign default role
        var userRole = new User_Role(user, role);
        user.addUserRole(userRole); // Correctly adds role to the list

        userRepository.save(user);

        var activationToken = jwtService.generateToken(user);
        return new AuthenticationResponse(activationToken);
    }


}
