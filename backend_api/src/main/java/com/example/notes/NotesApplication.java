package com.example.notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-NOTES-APP-BOOT
// User Story: As a developer, I need a Spring Boot application entrypoint.
// Acceptance Criteria: App starts successfully.
// GxP Impact: YES - Application availability and lifecycle logging.
// Risk Level: LOW
// Validation Protocol: VP-BOOT-001
// ============================================================================ 
 */
@SpringBootApplication
public class NotesApplication {

    // PUBLIC_INTERFACE
    public static void main(String[] args) {
        /** Application entrypoint. */
        SpringApplication.run(NotesApplication.class, args);
    }
}
