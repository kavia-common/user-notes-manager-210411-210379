package com.example.notes.service.mapper;

import com.example.notes.domain.Note;
import com.example.notes.web.dto.CreateNoteRequest;
import com.example.notes.web.dto.NoteResponse;
import com.example.notes.web.dto.UpdateNoteRequest;
import org.springframework.stereotype.Component;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-MAPPER
// User Story: Map between entities and DTOs.
// Acceptance Criteria: Correct field mappings.
// GxP Impact: YES - Accurate data representation.
// Risk Level: LOW
// Validation Protocol: VP-MAP-001
// ============================================================================
 */
@Component
public class NoteMapper {

    // PUBLIC_INTERFACE
    public Note toEntity(CreateNoteRequest req, String ownerId) {
        /** Map CreateNoteRequest to Note entity. */
        return new Note(req.getTitle(), req.getContent(), ownerId);
    }

    // PUBLIC_INTERFACE
    public void updateEntity(Note note, UpdateNoteRequest req) {
        /** Apply updates from DTO to entity. */
        note.setTitle(req.getTitle());
        note.setContent(req.getContent());
    }

    // PUBLIC_INTERFACE
    public NoteResponse toResponse(Note note) {
        /** Map Note entity to response DTO. */
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getOwnerId(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }
}
