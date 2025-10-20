package com.example.notes.it;

import com.example.notes.NotesApplication;
import com.example.notes.web.dto.CreateNoteRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
// ============================================================================
// INTEGRATION TESTS - Notes API
// ============================================================================
 */
@SpringBootTest(classes = NotesApplication.class)
@AutoConfigureMockMvc
public class NotesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper om = new ObjectMapper();

    @Test
    void fullCrudFlow() throws Exception {
        // create
        String createJson = om.writeValueAsString(new CreateNoteRequest("T", "C"));
        String body = mockMvc.perform(post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        // list
        mockMvc.perform(get("/api/v1/notes?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", greaterThanOrEqualTo(1)));

        // update
        mockMvc.perform(put("/api/v1/notes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"T2\",\"content\":\"C2\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("T2"));

        // delete
        mockMvc.perform(delete("/api/v1/notes/1"))
                .andExpect(status().isNoContent());
    }
}
