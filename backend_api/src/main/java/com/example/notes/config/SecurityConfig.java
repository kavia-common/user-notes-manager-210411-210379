package com.example.notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-SEC-PLACEHOLDER
// User Story: As a developer, I need role-based authorization scaffolding.
// Acceptance Criteria: Method-level security annotations can be used; endpoints are accessible for now.
// GxP Impact: YES - Access controls must be enforceable.
// Risk Level: MEDIUM
// Validation Protocol: VP-SEC-001
// ============================================================================
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    // PUBLIC_INTERFACE
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         * Placeholder security configuration.
         * - CSRF disabled for API simplicity (TODO: enable with token-based auth).
         * - All requests permitted for now; method security annotations will be enforced when authentication is added.
         * - TODO: Integrate real authentication provider (e.g., JWT).
         */
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/api-docs/**", "/", "/health", "/api/info", "/docs").permitAll()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
