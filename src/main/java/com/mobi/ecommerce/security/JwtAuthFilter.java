package com.mobi.ecommerce.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtAuthFilter extends OncePerRequestFilter {
    private  final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                   @NonNull HttpServletResponse response,
                                   @NonNull FilterChain filterChain) throws ServletException, IOException {
        // We don't do filter chain in case of this
        if(request.getServletPath().contains("/api/v1/auth")){
            filterChain.doFilter(request,response);
            return;
        }
        final String autHeader= request.getHeader("Authorization");
        final  String jwt;
        final String userEmail;
        if (autHeader==null || !autHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        // get the jwt
        jwt = autHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if(userEmail !=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            // validate the token
            if(jwtService.isTokenValid(jwt,userDetails))
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
