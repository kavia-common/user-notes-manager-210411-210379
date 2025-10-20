package com.example.notes.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-DTO-CREATE
// User Story: Validate note creation inputs.
// Acceptance Criteria: title 1..200, content <=5000.
// GxP Impact: YES
// Risk Level: LOW
// Validation Protocol: VP-DTO-001
// ============================================================================
 */
public class CreateNoteRequest {

    @Schema(description = "Title of the note", example = "Shopping List", minLength = 1, maxLength = 200, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(min = 1, max = 200)
    private String title;

    @Schema(description = "Content of the note", example = "Eggs, Milk, Bread", maxLength = 5000)
    @Size(max = 5000)
    private String content;

    public CreateNoteRequest() {}

    public CreateNoteRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // PUBLIC_INTERFACE
    public String getTitle() { return title; }
    // PUBLIC_INTERFACE
    public void setTitle(String title) { this.title = title; }
    // PUBLIC_INTERFACE
    public String getContent() { return content; }
    // PUBLIC_INTERFACE
    public void setContent(String content) { this.content = content; }
}
