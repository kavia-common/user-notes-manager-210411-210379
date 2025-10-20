package com.example.notes.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-NOTE-ENTITY
// User Story: Persist notes with title and content.
// Acceptance Criteria: Title 1..200, content <=5000, timestamps auto-managed.
// GxP Impact: YES - Data integrity and audit relevance.
// Risk Level: MEDIUM
// Validation Protocol: VP-ENT-001
// ============================================================================
 */
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 200)
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Size(max = 5000)
    @Column(name = "content", length = 5000)
    private String content;

    @Column(name = "owner_id", length = 100, nullable = false)
    private String ownerId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public Note() {}

    public Note(String title, String content, String ownerId) {
        this.title = title;
        this.content = content;
        this.ownerId = ownerId;
    }

    // Getters and setters
    // PUBLIC_INTERFACE
    public Long getId() { return id; }
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
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}
