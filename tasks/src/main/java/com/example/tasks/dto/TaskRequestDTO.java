package com.example.tasks.dto;

import com.example.tasks.model.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Schema(name = "TaskRequestDTO")
public class TaskRequestDTO {
    @NotBlank(message = "Title cannot be empty")
    @Schema(example = "Название задачи")
    private String title;

    @Schema(example = "Описание задачи")
    private String description;

    @NotNull(message = "Priority must be specified")
    @Schema(example = "MEDIUM")
    private TaskPriority priority;

    @FutureOrPresent(message = "Due Date cannot be in the past")
    @Schema(example = "2026-12-31T15:30:00")
    private LocalDateTime dueDate;

}
