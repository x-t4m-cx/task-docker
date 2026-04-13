package com.example.tasks.api;

import com.example.tasks.dto.TaskRequestDTO;
import com.example.tasks.dto.TaskResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TaskApi {

    @Operation(summary = "Get all tasks")
    List<TaskResponseDTO> getAll();

    @Operation(summary = "Get task by id")
    TaskResponseDTO getById(Long id);

    @Operation(summary = "Create new task")
    TaskResponseDTO create(TaskRequestDTO dto);

    @Operation(summary = "Update task")
    TaskResponseDTO update(Long id, TaskRequestDTO dto);

    @Operation(summary = "Delete task")
    void delete(Long id);
}
