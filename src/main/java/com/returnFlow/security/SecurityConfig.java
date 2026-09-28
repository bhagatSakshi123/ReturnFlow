package com.returnFlow.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Authentication
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        // Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Public product viewing
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/api/products/**"
                        ).permitAll()

                        // Admin
                        .requestMatchers(
                                "/api/users/**"
                        ).hasRole("ADMIN")

                        // Seller + Admin
                        .requestMatchers(
                                "/api/inspections/**"
                        ).hasAnyRole("SELLER", "ADMIN")

                        .requestMatchers(
                                "/api/refunds/**"
                        ).hasAnyRole("SELLER", "ADMIN")

                        // Authenticated users
                                // Seller + Admin
                                .requestMatchers(
                                        "/api/disputes/**"
                                ).hasAnyRole("SELLER", "ADMIN")

// Authenticated users
                                .requestMatchers(
                                        "/api/returns/**",
                                        "/api/orders/**",
                                        "/api/order-items/**"
                                ).authenticated()

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}