package com.example.notes.web.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.util.Map;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-API-ERROR
// User Story: Consistent error response structure.
// Acceptance Criteria: code, message, details, timestamp, path.
// GxP Impact: YES - Traceability and diagnostics.
// Risk Level: LOW
// Validation Protocol: VP-ERR-001
// ============================================================================
 */
public class ApiError {
    private String code;
    private String message;
    private Map<String, Object> details;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime timestamp;
    private String path;

    public ApiError() {}

    public ApiError(String code, String message, Map<String, Object> details, OffsetDateTime timestamp, String path) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
        this.path = path;
    }

    // PUBLIC_INTERFACE
    public String getCode() { return code; }
    // PUBLIC_INTERFACE
    public void setCode(String code) { this.code = code; }
    // PUBLIC_INTERFACE
    public String getMessage() { return message; }
    // PUBLIC_INTERFACE
    public void setMessage(String message) { this.message = message; }
    // PUBLIC_INTERFACE
    public Map<String, Object> getDetails() { return details; }
    // PUBLIC_INTERFACE
    public void setDetails(Map<String, Object> details) { this.details = details; }
    // PUBLIC_INTERFACE
    public OffsetDateTime getTimestamp() { return timestamp; }
    // PUBLIC_INTERFACE
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
    // PUBLIC_INTERFACE
    public String getPath() { return path; }
    // PUBLIC_INTERFACE
    public void setPath(String path) { this.path = path; }
}
