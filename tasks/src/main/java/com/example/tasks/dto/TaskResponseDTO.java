package com.example.tasks.dto;

import com.example.tasks.model.TaskPriority;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private TaskPriority priority;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
}
