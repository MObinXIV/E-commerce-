package com.mobi.ecommerce.auth;

import com.mobi.ecommerce.email.EmailService;
import com.mobi.ecommerce.role.RoleRepository;
import com.mobi.ecommerce.role.RoleType;
import com.mobi.ecommerce.role.User_Role;
import com.mobi.ecommerce.security.JwtService;
import com.mobi.ecommerce.user.User;
import com.mobi.ecommerce.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
//@Tag(name = "Authentication")
public class AuthenticationService {
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private  final EmailService emailService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RoleRepository roleRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
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

    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request){
      var auth=  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String,Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("fullName",user.fullName());

        var jwtToken = jwtService.generateToken(claims,user);
        return new AuthenticationResponse(jwtToken);
    }
    @Transactional
    public void activateAccount(String token) {
        String email = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the activation token is expired
        if (jwtService.isTokenExpired(token)) {
            throw new RuntimeException("Activation token has expired. Request a new one.");
        }

        // Enable user account
        user.setAccountEnabled(true);
        userRepository.save(user);

        System.out.println("✅ Account activated for: " + email);
    }

}
