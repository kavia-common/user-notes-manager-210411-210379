package com.example.notes.repository;

import com.example.notes.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-NOTE-REPO
// User Story: Data access for notes with pagination.
// Acceptance Criteria: Standard CRUD, page listing.
// GxP Impact: YES - Data integrity.
// Risk Level: LOW
// Validation Protocol: VP-REPO-001
// ============================================================================
 */
public interface NoteRepository extends JpaRepository<Note, Long> {

    // PUBLIC_INTERFACE
    Page<Note> findAllByOwnerId(String ownerId, Pageable pageable);
}
