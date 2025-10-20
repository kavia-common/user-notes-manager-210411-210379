package com.example.notes.web;

import com.example.notes.service.NoteService;
import com.example.notes.web.dto.CreateNoteRequest;
import com.example.notes.web.dto.NoteResponse;
import com.example.notes.web.dto.UpdateNoteRequest;
import com.example.notes.web.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
// ============================================================================
// REQUIREMENT TRACEABILITY
// ============================================================================
// Requirement ID: REQ-NOTE-CTRL
// User Story: Expose CRUD endpoints for notes.
// Acceptance Criteria: POST/GET(list)/GET(id)/PUT/DELETE with pagination and docs.
// GxP Impact: YES
// Risk Level: HIGH
// Validation Protocol: VP-CTRL-001
// ============================================================================
 */
@RestController
@RequestMapping("/api/v1/notes")
@Tag(name = "Notes", description = "CRUD endpoints for managing notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(
            summary = "Create note",
            description = "Creates a new note for the current user.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    public ResponseEntity<NoteResponse> create(
            @Valid @RequestBody CreateNoteRequest request,
            HttpServletRequest httpRequest
    ) {
        NoteResponse response = noteService.create(request, httpRequest.getRequestURI());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(
            summary = "List notes",
            description = "Returns paginated list of notes for the current user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PageNoteResponseSchema.class)))
            }
    )
    public ResponseEntity<Page<NoteResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction,
            HttpServletRequest httpRequest
    ) {
        Page<NoteResponse> resp = noteService.list(page, size, sort, direction, httpRequest.getRequestURI());
        return ResponseEntity.ok(resp);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(
            summary = "Get note",
            description = "Returns a note by ID if the current user is the owner or an admin.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = NoteResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    public ResponseEntity<NoteResponse> get(
            @PathVariable Long id,
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.ok(noteService.get(id, httpRequest.getRequestURI()));
    }

    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(
            summary = "Update note",
            description = "Updates a note's title/content; logs before/after snapshot in audit trail.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = NoteResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    public ResponseEntity<NoteResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateNoteRequest request,
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.ok(noteService.update(id, request, httpRequest.getRequestURI()));
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete note",
            description = "Deletes a note; logs before snapshot in audit trail.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ApiError.class)))
            }
    )
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            HttpServletRequest httpRequest
    ) {
        noteService.delete(id, httpRequest.getRequestURI());
        return ResponseEntity.noContent().build();
    }

    // Helper schema wrapper to represent Page<NoteResponse> in OpenAPI docs.
    @Schema(name = "PageNoteResponse")
    static class PageNoteResponseSchema extends PageImpl<NoteResponse> {
        public PageNoteResponseSchema() { super(java.util.List.of()); }
    }
}
