package com.example.notes.service;

import com.example.notes.domain.AuditLog;
import com.example.notes.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-AUDIT-SVC
// User Story: Log audit entries for CRUD operations.
// Acceptance Criteria: Persist logs with userId, action, timestamps, before/after.
// GxP Impact: YES
// Risk Level: HIGH
// Validation Protocol: VP-AUD-002
// ============================================================================
 */
@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void log(String userId, String action, Long noteId, String beforeState, String afterState, String path, String reason) {
        /** Persist a generic audit log entry. */
        AuditLog log = new AuditLog(userId, action, noteId, OffsetDateTime.now(), beforeState, afterState, path, reason);
        auditLogRepository.save(log);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void logCreate(String userId, Long noteId, String afterState, String path) {
        /** Shortcut for CREATE operations. */
        log(userId, "CREATE", noteId, null, afterState, path, null);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void logRead(String userId, Long noteId, String path) {
        /** Shortcut for READ operations (no before/after). */
        log(userId, "READ", noteId, null, null, path, null);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void logUpdate(String userId, Long noteId, String beforeState, String afterState, String path) {
        /** Shortcut for UPDATE operations. */
        log(userId, "UPDATE", noteId, beforeState, afterState, path, null);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void logDelete(String userId, Long noteId, String beforeState, String path) {
        /** Shortcut for DELETE operations. */
        log(userId, "DELETE", noteId, beforeState, null, path, null);
    }
}
