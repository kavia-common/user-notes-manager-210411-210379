package com.example.notes.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-DTO-RESPONSE
// User Story: Return note data to clients.
// Acceptance Criteria: Includes id, title, content, ownerId, timestamps.
// GxP Impact: YES
// Risk Level: LOW
// Validation Protocol: VP-DTO-003
// ============================================================================
 */
public class NoteResponse {

    @Schema(description = "Note ID", example = "1")
    private Long id;

    @Schema(description = "Title", example = "Shopping List")
    private String title;

    @Schema(description = "Content", example = "Eggs, Milk, Bread")
    private String content;

    @Schema(description = "Owner user ID", example = "demo-user")
    private String ownerId;

    @Schema(description = "Creation timestamp (ISO8601)")
    private OffsetDateTime createdAt;

    @Schema(description = "Last update timestamp (ISO8601)")
    private OffsetDateTime updatedAt;

    public NoteResponse() {}

    public NoteResponse(Long id, String title, String content, String ownerId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public String getTitle() { return title; }
    // PUBLIC_INTERFACE
    public void setTitle(String title) { this.title = title; }
    // PUBLIC_INTERFACE
    public String getContent() { return content; }
    // PUBLIC_INTERFACE
    public void setContent(String content) { this.content = content; }
    // PUBLIC_INTERFACE
    public String getOwnerId() { return ownerId; }
    // PUBLIC_INTERFACE
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    // PUBLIC_INTERFACE
    public OffsetDateTime getCreatedAt() { return createdAt; }
    // PUBLIC_INTERFACE
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    // PUBLIC_INTERFACE
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    // PUBLIC_INTERFACE
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
