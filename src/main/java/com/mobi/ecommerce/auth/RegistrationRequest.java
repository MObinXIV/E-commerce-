package com.mobi.ecommerce.auth;

public class RegesterationRequest {
    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email(message = "Email is not well formated")
    @NotEmpty(message = "Email name is required")
    @NotBlank(message = "Email name is required")
    private String email;
    @Size(min = 8, message = "Password is at least 8 characters")
    private String password;
}
