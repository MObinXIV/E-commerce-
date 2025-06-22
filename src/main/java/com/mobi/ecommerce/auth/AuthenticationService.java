package com.mobi.ecommerce.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobi.ecommerce.role.Role;
import com.mobi.ecommerce.role.RoleRepository;
import com.mobi.ecommerce.role.RoleType;
import com.mobi.ecommerce.role.User_Role;
import com.mobi.ecommerce.security.JwtService;
import com.mobi.ecommerce.user.User;
import com.mobi.ecommerce.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;


@Service
//@Tag(name = "Authentication")
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

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
        Role userRole = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> new RuntimeException("Default role USER not found!"));

        var user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setGender(request.getGender());
        user.setAccountLocked(false);
        user.setAccountEnabled(true);
        user.addUserRole(new User_Role(user, userRole)); // Correctly adds role to the list
//        sendEmailValidation(user);
        userRepository.save(user);

        var activationToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new AuthenticationResponse(activationToken, refreshToken);
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());

        var jwtToken = jwtService.generateToken(claims, user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String autHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
//        final String userEmail;
        if (autHeader == null || !autHeader.startsWith("Bearer ")) {
            return;
        }
        // get the jwt
        refreshToken = autHeader.substring(7);
//        System.out.println("Received JWT: " + jwt); // Debugging Log

        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null ) {
            var user = this.userRepository.findByEmail(userEmail).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var authResponse= new AuthenticationResponse(accessToken,refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }

}