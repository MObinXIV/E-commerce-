package com.mobi.ecommerce.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final  AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegistrationRequest request){
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
                                                                @RequestBody @Valid AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam String token) {
        authenticationService.activateAccount(token);
        return ResponseEntity.ok("Account activated successfully.");
    }

}
