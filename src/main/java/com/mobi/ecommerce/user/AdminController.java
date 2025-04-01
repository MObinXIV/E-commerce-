package com.mobi.ecommerce.user;

import com.mobi.ecommerce.auth.AuthenticationRequest;
import com.mobi.ecommerce.auth.AuthenticationResponse;
import com.mobi.ecommerce.auth.RegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
   private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
     // Only an admin can create another admin
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody @Valid RegistrationRequest request) {
        return ResponseEntity.ok(adminService.registerAdmin(request));
    }

    // ✅ Admin Login
//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(adminService.login(request));
    }
}
