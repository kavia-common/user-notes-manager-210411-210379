package com.example.notes.service;

import com.example.notes.domain.Note;
import com.example.notes.repository.NoteRepository;
import com.example.notes.service.mapper.NoteMapper;
import com.example.notes.web.dto.CreateNoteRequest;
import com.example.notes.web.dto.NoteResponse;
import com.example.notes.web.dto.UpdateNoteRequest;
import com.example.notes.web.security.CurrentUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.NoSuchElementException;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-NOTE-SVC
// User Story: Implement CRUD operations with validation and audit.
// Acceptance Criteria: Create, list (paginated), get, update, delete with audit logs and RBAC hooks.
// GxP Impact: YES
// Risk Level: HIGH
// Validation Protocol: VP-SVC-001
// ============================================================================
 */
@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final AuditService auditService;
    private final CurrentUser currentUser;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NoteService(NoteRepository noteRepository, NoteMapper noteMapper, AuditService auditService, CurrentUser currentUser) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
        this.auditService = auditService;
        this.currentUser = currentUser;
    }

    // PUBLIC_INTERFACE
    @Transactional
    public NoteResponse create(CreateNoteRequest request, String requestPath) {
        /** Create a note and log CREATE audit. */
        Assert.notNull(request, "request must not be null");
        String userId = currentUser.getUserId();

        Note entity = noteMapper.toEntity(request, userId);
        Note saved = noteRepository.save(entity);

        auditService.logCreate(userId, saved.getId(), toJsonQuiet(saved), requestPath);
        return noteMapper.toResponse(saved);
    }

    // PUBLIC_INTERFACE
    @Transactional(readOnly = true)
    public Page<NoteResponse> list(int page, int size, String sortBy, String direction, String requestPath) {
        /** List paginated notes for current user, log READ audit without details. */
        String userId = currentUser.getUserId();
        Sort sort = Sort.by("createdAt");
        if (sortBy != null && !sortBy.isBlank()) {
            sort = Sort.by(sortBy);
        }
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 200), sort);
        Page<Note> pageResult = noteRepository.findAllByOwnerId(userId, pageable);
        // We can optionally log a READ event; not per record to avoid noise
        auditService.logRead(userId, null, requestPath);
        return pageResult.map(noteMapper::toResponse);
    }

    // PUBLIC_INTERFACE
    @Transactional(readOnly = true)
    public NoteResponse get(Long id, String requestPath) {
        /** Get a note by id (ownership enforced), log READ audit. */
        String userId = currentUser.getUserId();
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Note not found"));
        enforceOwnershipOrAdmin(userId, note.getOwnerId());
        auditService.logRead(userId, id, requestPath);
        return noteMapper.toResponse(note);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public NoteResponse update(Long id, UpdateNoteRequest request, String requestPath) {
        /** Update a note and log before/after in audit. */
        Assert.notNull(request, "request must not be null");
        String userId = currentUser.getUserId();

        Note note = noteRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Note not found"));
        enforceOwnershipOrAdmin(userId, note.getOwnerId());
        String before = toJsonQuiet(note);

        noteMapper.updateEntity(note, request);
        Note saved = noteRepository.save(note);

        auditService.logUpdate(userId, id, before, toJsonQuiet(saved), requestPath);
        return noteMapper.toResponse(saved);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void delete(Long id, String requestPath) {
        /** Delete a note and log DELETE with before snapshot. */
        String userId = currentUser.getUserId();
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Note not found"));
        enforceOwnershipOrAdmin(userId, note.getOwnerId());
        String before = toJsonQuiet(note);
        noteRepository.delete(note);
        auditService.logDelete(userId, id, before, requestPath);
    }

    // RBAC check stub; replace with @PreAuthorize and real auth as needed
    private void enforceOwnershipOrAdmin(String userId, String ownerId) {
        if (userId == null) throw new SecurityException("Unauthenticated");
        boolean isOwner = userId.equals(ownerId);
        boolean isAdmin = currentUser.hasRole("ROLE_ADMIN");
        if (!isOwner && !isAdmin) {
            throw new SecurityException("Forbidden: user not owner or admin");
        }
    }

    private String toJsonQuiet(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            // In audit, swallow serialization issues but return a minimal representation
            return "{\"error\":\"serialization\"}";
        }
    }
}
