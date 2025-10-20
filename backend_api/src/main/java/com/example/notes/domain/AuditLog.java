package com.example.notes.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-AUDIT-ENTITY
// User Story: Persist audit events for note operations.
// Acceptance Criteria: Log userId, timestamp, action, noteId, before/after, path optional.
// GxP Impact: YES - Audit trail.
// Risk Level: HIGH
// Validation Protocol: VP-AUD-001
// ============================================================================
 */
@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actor_user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "action", nullable = false, length = 20)
    private String action;

    @Column(name = "note_id")
    private Long noteId;

    @Column(name = "event_time", nullable = false)
    private OffsetDateTime timestamp;

    @Lob
    @Column(name = "before_state")
    private String beforeState;

    @Lob
    @Column(name = "after_state")
    private String afterState;

    @Column(name = "request_path")
    private String path;

    @Column(name = "reason")
    private String reason;

    public AuditLog() {}

    public AuditLog(String userId, String action, Long noteId, OffsetDateTime timestamp, String beforeState, String afterState, String path, String reason) {
        this.userId = userId;
        this.action = action;
        this.noteId = noteId;
        this.timestamp = timestamp;
        this.beforeState = beforeState;
        this.afterState = afterState;
        this.path = path;
        this.reason = reason;
    }

    // Getters and setters
    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public String getUserId() { return userId; }
    // PUBLIC_INTERFACE
    public void setUserId(String userId) { this.userId = userId; }
    // PUBLIC_INTERFACE
    public String getAction() { return action; }
    // PUBLIC_INTERFACE
    public void setAction(String action) { this.action = action; }
    // PUBLIC_INTERFACE
    public Long getNoteId() { return noteId; }
    // PUBLIC_INTERFACE
    public void setNoteId(Long noteId) { this.noteId = noteId; }
    // PUBLIC_INTERFACE
    public OffsetDateTime getTimestamp() { return timestamp; }
    // PUBLIC_INTERFACE
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
    // PUBLIC_INTERFACE
    public String getBeforeState() { return beforeState; }
    // PUBLIC_INTERFACE
    public void setBeforeState(String beforeState) { this.beforeState = beforeState; }
    // PUBLIC_INTERFACE
    public String getAfterState() { return afterState; }
    // PUBLIC_INTERFACE
    public void setAfterState(String afterState) { this.afterState = afterState; }
    // PUBLIC_INTERFACE
    public String getPath() { return path; }
    // PUBLIC_INTERFACE
    public void setPath(String path) { this.path = path; }
    // PUBLIC_INTERFACE
    public String getReason() { return reason; }
    // PUBLIC_INTERFACE
    public void setReason(String reason) { this.reason = reason; }
}
