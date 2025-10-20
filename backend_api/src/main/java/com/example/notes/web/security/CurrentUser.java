package com.example.notes.web.security;

import org.springframework.stereotype.Component;

import java.util.Set;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-CURRENT-USER
// User Story: Access current user info for RBAC and audit.
// Acceptance Criteria: Provide placeholder userId and roles.
// GxP Impact: YES - Attributable audit entries.
// Risk Level: MEDIUM
// Validation Protocol: VP-SEC-USER-001
// ============================================================================
 */
@Component
public class CurrentUser {

    // PUBLIC_INTERFACE
    public String getUserId() {
        /** Returns placeholder user id. TODO: Integrate real authentication context */
        return "demo-user";
    }

    // PUBLIC_INTERFACE
    public Set<String> getRoles() {
        /** Returns placeholder roles. TODO: Map from authentication principal */
        return Set.of("ROLE_USER");
    }

    // PUBLIC_INTERFACE
    public boolean hasRole(String role) {
        /** Check role membership. */
        return getRoles().contains(role);
    }
}
