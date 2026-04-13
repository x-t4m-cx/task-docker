package com.example.tasks.controller;

import com.example.tasks.dto.TaskRequestDTO;
import com.example.tasks.dto.TaskResponseDTO;
import com.example.tasks.model.TaskPriority;
import com.example.tasks.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    void getAll_shouldReturnTasks() throws Exception {
        when(taskService.getAll()).thenReturn(List.of(buildResponse(1L)));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Task-1"));
    }

    @Test
    void getById_shouldReturnTask() throws Exception {
        when(taskService.getById(2L)).thenReturn(buildResponse(2L));

        mockMvc.perform(get("/api/tasks/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("Task-2"));
    }

    @Test
    void getAllSorted_shouldReturnTasks() throws Exception {
        when(taskService.getAllSorted()).thenReturn(List.of(buildResponse(3L)));

        mockMvc.perform(get("/api/tasks/sorted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3));
    }

    @Test
    void create_shouldReturnCreatedTask() throws Exception {
        TaskRequestDTO request = buildRequest();
        when(taskService.create(any(TaskRequestDTO.class))).thenReturn(buildResponse(4L));

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.title").value("Task-4"));
    }

    @Test
    void update_shouldReturnUpdatedTask() throws Exception {
        TaskRequestDTO request = buildRequest();
        when(taskService.update(eq(5L), any(TaskRequestDTO.class))).thenReturn(buildResponse(5L));

        mockMvc.perform(put("/api/tasks/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        doNothing().when(taskService).delete(6L);

        mockMvc.perform(delete("/api/tasks/6"))
                .andExpect(status().isNoContent());

        verify(taskService).delete(6L);
    }

    private TaskRequestDTO buildRequest() {
        TaskRequestDTO request = new TaskRequestDTO();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setPriority(TaskPriority.MEDIUM);
        request.setDueDate(LocalDateTime.now().plusDays(1));
        return request;
    }

    private TaskResponseDTO buildResponse(Long id) {
        return TaskResponseDTO.builder()
                .id(id)
                .title("Task-" + id)
                .description("Description-" + id)
                .priority(TaskPriority.MEDIUM)
                .dueDate(LocalDateTime.of(2030, 1, 1, 10, 0))
                .createdAt(LocalDateTime.of(2026, 1, 1, 10, 0))
                .build();
    }
}
