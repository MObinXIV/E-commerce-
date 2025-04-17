package com.mobi.ecommerce.auth;

import com.mobi.ecommerce.role.Role;
import com.mobi.ecommerce.role.RoleRepository;
import com.mobi.ecommerce.role.RoleType;
import com.mobi.ecommerce.role.User_Role;
import com.mobi.ecommerce.security.JwtService;
import com.mobi.ecommerce.user.User;
import com.mobi.ecommerce.user.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        user.addUserRole(new User_Role(user,userRole)); // Correctly adds role to the list
//        sendEmailValidation(user);
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
//    @Transactional
//    public void activateAccount(String token) {
//        System.out.println("🔍 Received token: " + token);
//        String email = jwtService.extractUsername(token);
//        System.out.println("📧 Extracted email: " + email);
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (jwtService.isTokenExpired(token)) {
//            throw new RuntimeException("Activation token has expired. Request a new one.");
//        }
//
//        // ✅ Modify the field directly
//        user.setAccountEnabled(true);
//
//    }

//    private void sendEmailValidation(User user) {
//        var token = jwtService.generateToken(user);
//        System.out.println("📧 Sending activation email to: " + user.getEmail());
//        System.out.println("🔗 Activation link: " + activationUrl + "?token=" + token);
//
//        try {
//            emailService.sendEmail(
//                    user.getEmail(),
//                    user.fullName(),
//                    EmailTemplateName.ACTIVE_ACCOUNT,
//                    activationUrl,
//                    token,
//                    "Activate Your Account"
//            );
//            System.out.println("📧 Activation email sent to: " + user.getEmail());
//        } catch (Exception e) {  // Catch generic exception instead
//            throw new RuntimeException("Failed to send activation email", e);
//        }
//    }

}
