package com.example.notes.repository;

import com.example.notes.domain.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-AUDIT-REPO
// User Story: Persist audit logs.
// Acceptance Criteria: Standard CRUD persist capability.
// GxP Impact: YES
// Risk Level: LOW
// Validation Protocol: VP-REPO-002
// ============================================================================
 */
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
