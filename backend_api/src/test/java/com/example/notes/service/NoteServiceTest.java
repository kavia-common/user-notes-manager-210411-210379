package com.example.notes.service;

import com.example.notes.domain.Note;
import com.example.notes.repository.NoteRepository;
import com.example.notes.service.mapper.NoteMapper;
import com.example.notes.web.dto.CreateNoteRequest;
import com.example.notes.web.dto.UpdateNoteRequest;
import com.example.notes.web.security.CurrentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
// ============================================================================
// UNIT TESTS - NoteService
// ============================================================================
 */
class NoteServiceTest {

    private NoteRepository noteRepository;
    private NoteMapper noteMapper;
    private AuditService auditService;
    private CurrentUser currentUser;
    private NoteService noteService;

    @BeforeEach
    void setup() {
        noteRepository = mock(NoteRepository.class);
        noteMapper = new NoteMapper();
        auditService = mock(AuditService.class);
        currentUser = mock(CurrentUser.class);
        when(currentUser.getUserId()).thenReturn("demo-user");

        noteService = new NoteService(noteRepository, noteMapper, auditService, currentUser);
    }

    @Test
    void create_shouldCreateNote_andLogAudit() {
        CreateNoteRequest req = new CreateNoteRequest("Title", "Content");
        Note saved = new Note("Title", "Content", "demo-user");
        // simulate DB assigned id via save
        Note persisted = new Note("Title", "Content", "demo-user");
        // use reflection or setter - we can capture return and set via repository mock
        when(noteRepository.save(any())).thenAnswer(invocation -> {
            Note arg = invocation.getArgument(0);
            // crude id assignment via a spy object
            try {
                var field = Note.class.getDeclaredField("id");
                field.setAccessible(true);
                field.set(arg, 1L);
            } catch (Exception ignored) { }
            return arg;
        });

        var resp = noteService.create(req, "/api/v1/notes");

        assertNotNull(resp.getId());
        assertEquals("Title", resp.getTitle());
        verify(auditService, times(1)).logCreate(eq("demo-user"), eq(resp.getId()), anyString(), eq("/api/v1/notes"));
    }

    @Test
    void update_shouldApplyChanges_andLogBeforeAfter() {
        Note existing = new Note("Old", "OldC", "demo-user");
        try {
            var field = Note.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(existing, 5L);
        } catch (Exception ignored) { }

        when(noteRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(noteRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateNoteRequest req = new UpdateNoteRequest("New", "NewC");
        var resp = noteService.update(5L, req, "/api/v1/notes/5");

        assertEquals("New", resp.getTitle());
        ArgumentCaptor<String> beforeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> afterCaptor = ArgumentCaptor.forClass(String.class);
        verify(auditService).logUpdate(eq("demo-user"), eq(5L), beforeCaptor.capture(), afterCaptor.capture(), eq("/api/v1/notes/5"));
        assertTrue(beforeCaptor.getValue().contains("Old"));
        assertTrue(afterCaptor.getValue().contains("New"));
    }

    @Test
    void delete_shouldRemove_andLogDelete() {
        Note existing = new Note("T", "C", "demo-user");
        try {
            var field = Note.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(existing, 7L);
        } catch (Exception ignored) { }
        when(noteRepository.findById(7L)).thenReturn(Optional.of(existing));

        noteService.delete(7L, "/api/v1/notes/7");

        verify(noteRepository).delete(existing);
        verify(auditService).logDelete(eq("demo-user"), eq(7L), anyString(), eq("/api/v1/notes/7"));
    }

    @Test
    void get_shouldThrowNotFound_whenMissing() {
        when(noteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> noteService.get(99L, "/api/v1/notes/99"));
    }

    @Test
    void list_shouldReturnPage() {
        Page<Note> page = new PageImpl<>(List.of(new Note("A", "B", "demo-user")));
        when(noteRepository.findAllByOwnerId(eq("demo-user"), any(Pageable.class))).thenReturn(page);

        var resp = noteService.list(0, 10, "createdAt", "desc", "/api/v1/notes");

        assertEquals(1, resp.getTotalElements());
        verify(auditService).logRead(eq("demo-user"), isNull(), eq("/api/v1/notes"));
    }
}
