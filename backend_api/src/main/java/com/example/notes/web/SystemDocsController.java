package com.example.notes.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-DOCS-NOTE
// User Story: Provide docs endpoints including WebSocket usage note (none implemented).
// Acceptance Criteria: Endpoint describes real-time usage note.
// GxP Impact: NO
// Risk Level: LOW
// Validation Protocol: VP-DOCS-001
// ============================================================================
 */
@RestController
@Tag(name = "System", description = "System and documentation endpoints")
public class SystemDocsController {

    // PUBLIC_INTERFACE
    @GetMapping("/api/info")
    @Operation(summary = "Application info", description = "Returns application information and real-time connection notes. No WebSocket endpoints are currently implemented.")
    public String info() {
        return "Notes API. WebSocket: none. Use REST endpoints.";
    }
}
