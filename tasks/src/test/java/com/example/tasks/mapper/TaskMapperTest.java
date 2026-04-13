package com.example.tasks.mapper;

import com.example.tasks.dto.TaskRequestDTO;
import com.example.tasks.dto.TaskResponseDTO;
import com.example.tasks.model.Task;
import com.example.tasks.model.TaskPriority;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskMapperTest {

    private final TaskMapper mapper = new TaskMapper();

    @Test
    void toEntity_shouldMapFieldsAndSetCreatedAt() {
        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setTitle("Title");
        dto.setDescription("Description");
        dto.setPriority(TaskPriority.HIGH);
        dto.setDueDate(LocalDateTime.of(2030, 1, 1, 10, 0));

        Task entity = mapper.toEntity(dto);

        assertEquals("Title", entity.getTitle());
        assertEquals("Description", entity.getDescription());
        assertEquals(TaskPriority.HIGH, entity.getPriority());
        assertEquals(LocalDateTime.of(2030, 1, 1, 10, 0), entity.getDueDate());
        assertNotNull(entity.getCreatedAt());
    }

    @Test
    void toDto_shouldMapAllFields() {
        LocalDateTime createdAt = LocalDateTime.of(2026, 1, 1, 12, 0);
        LocalDateTime dueDate = LocalDateTime.of(2026, 2, 1, 12, 0);

        Task entity = Task.builder()
                .id(10L)
                .title("Task title")
                .description("Task description")
                .priority(TaskPriority.MEDIUM)
                .dueDate(dueDate)
                .createdAt(createdAt)
                .build();

        TaskResponseDTO dto = mapper.toDTO(entity);

        assertEquals(10L, dto.getId());
        assertEquals("Task title", dto.getTitle());
        assertEquals("Task description", dto.getDescription());
        assertEquals(TaskPriority.MEDIUM, dto.getPriority());
        assertEquals(dueDate, dto.getDueDate());
        assertEquals(createdAt, dto.getCreatedAt());
    }
}
