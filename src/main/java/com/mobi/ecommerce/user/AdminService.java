package com.mobi.ecommerce.user;

import com.mobi.ecommerce.auth.AuthenticationRequest;
import com.mobi.ecommerce.auth.AuthenticationResponse;
import com.mobi.ecommerce.auth.RegistrationRequest;
import com.mobi.ecommerce.role.Role;
import com.mobi.ecommerce.role.RoleRepository;
import com.mobi.ecommerce.role.RoleType;
import com.mobi.ecommerce.role.User_Role;
import com.mobi.ecommerce.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private  final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AdminService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthenticationResponse registerAdmin(RegistrationRequest request){
        // Check if the role is already exist
        if(userRepository.existsByRole(RoleType.ADMIN))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"admin is already exists");

        Role role = roleRepository.findByName(RoleType.ADMIN).orElseThrow(() -> new IllegalStateException("ADMIN role not found"));
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }
        User admin = new User();
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setPhoneNumber(request.getPhoneNumber());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setAccountLocked(false);
        admin.setAccountEnabled(true);
        admin.addUserRole(new User_Role(admin,role));
        userRepository.save(admin);
        String token = jwtService.generateToken(admin);
        return new AuthenticationResponse(token);    }

    public AuthenticationResponse login(AuthenticationRequest request){
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var admin =(User) auth.getPrincipal();
        if (!admin.hasRole(RoleType.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied! Not an admin.");

        String token = jwtService.generateToken(admin);
        return new AuthenticationResponse(token);
    }
}
