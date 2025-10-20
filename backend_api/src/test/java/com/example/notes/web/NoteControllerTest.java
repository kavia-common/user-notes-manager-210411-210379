package com.example.notes.web;

import com.example.notes.service.NoteService;
import com.example.notes.web.dto.CreateNoteRequest;
import com.example.notes.web.dto.NoteResponse;
import com.example.notes.web.dto.UpdateNoteRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
// ============================================================================
// UNIT TESTS - NoteController
// ============================================================================
 */
@WebMvcTest(controllers = NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void create_shouldReturn201() throws Exception {
        NoteResponse resp = new NoteResponse(1L, "T", "C", "demo-user", null, null);
        Mockito.when(noteService.create(any(CreateNoteRequest.class), anyString())).thenReturn(resp);

        mockMvc.perform(post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateNoteRequest("T", "C"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void create_shouldValidate_andReturn400() throws Exception {
        mockMvc.perform(post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\",\"content\":\"x\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    void list_shouldReturnPage() throws Exception {
        Mockito.when(noteService.list(anyInt(), anyInt(), anyString(), anyString(), anyString()))
                .thenReturn(new PageImpl<>(List.of(new NoteResponse(1L, "T", "C", "demo-user", null, null))));

        mockMvc.perform(get("/api/v1/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void update_shouldReturn200() throws Exception {
        NoteResponse resp = new NoteResponse(1L, "New", "C", "demo-user", null, null);
        Mockito.when(noteService.update(eq(1L), any(UpdateNoteRequest.class), anyString())).thenReturn(resp);

        mockMvc.perform(put("/api/v1/notes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateNoteRequest("New", "C"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New"));
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/notes/1"))
                .andExpect(status().isNoContent());
    }
}
