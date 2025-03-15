package com.mobi.ecommerce.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansConfig {

     private final UserDetailsService userDetailsService;

    public BeansConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        // Create an instance of DaoAuthenticationProvider
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Set the UserDetailsService to retrieve user authentication details from the database
        authProvider.setUserDetailsService(userDetailsService);

        // Set the password encoder to securely verify user passwords
        authProvider.setPasswordEncoder(passwordEncoder());

        // Return the configured authentication provider
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
