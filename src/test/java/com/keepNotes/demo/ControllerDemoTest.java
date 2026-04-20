package com.keepNotes.demo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ControllerDemoTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String createUserAndReturnEmail() throws Exception {
        String email = "user-" + UUID.randomUUID();

        Map<String, Object> user = new HashMap<>();
        user.put("userName", "Vaishu");
        user.put("userEmail", email);
        user.put("notes", java.util.Collections.emptyList());

        mockMvc.perform(post("/users/addUser")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isOk());

        return email;
    }

    @Test
    void createUser_happyPath() throws Exception {
        String email = "user-" + UUID.randomUUID();

        Map<String, Object> user = new HashMap<>();
        user.put("userName", "Test User");
        user.put("userEmail", email);
        user.put("notes", java.util.Collections.emptyList());

        mockMvc.perform(post("/users/addUser")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userName").value("Test User"))
        .andExpect(jsonPath("$.userEmail").value(email));
    }

    @Test
    void getUser_happyPath() throws Exception {
        String email = createUserAndReturnEmail();

        mockMvc.perform(
                get("/users/1")
                        .param("userEmail", email)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userEmail").value(email));
    }

    @Test
    void addNote_happyPath() throws Exception {
        String email = createUserAndReturnEmail();

        Map<String, Object> note = new HashMap<>();
        note.put("title", "Buy milk");
        note.put("body", "2 liters");
        note.put("priority", 1);

        mockMvc.perform(
                post("/users/notes/addNote")
                        .param("userEmail", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Buy milk"))
        .andExpect(jsonPath("$.body").value("2 liters"))
        .andExpect(jsonPath("$.priority").value(1))
        .andExpect(jsonPath("$.createdAt").exists())
        .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void updateNote_happyPath() throws Exception {
        String email = createUserAndReturnEmail();

        Map<String, Object> note = new HashMap<>();
        note.put("title", "Old title");
        note.put("body", "Old body");
        note.put("priority", 1);

        mockMvc.perform(
                post("/users/notes/addNote")
                        .param("userEmail", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note))
        ).andExpect(status().isOk());

        Map<String, Object> updated = new HashMap<>();
        updated.put("title", "New title");
        updated.put("body", "New body");
        updated.put("priority", 2);

        mockMvc.perform(
                put("/users/{email}/notes/{index}", email, 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("New title"))
        .andExpect(jsonPath("$.body").value("New body"))
        .andExpect(jsonPath("$.priority").value(2))
        .andExpect(jsonPath("$.updatedAt").exists());
    }
}