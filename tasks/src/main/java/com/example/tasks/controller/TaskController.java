package com.example.tasks.controller;

import com.example.tasks.api.TaskApi;
import com.example.tasks.dto.TaskRequestDTO;
import com.example.tasks.dto.TaskResponseDTO;
import com.example.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController implements TaskApi {
    private final TaskService service;

    @GetMapping
    public List<TaskResponseDTO> getAll() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public TaskResponseDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }
    @GetMapping("/sorted")
    public List<TaskResponseDTO> getAllSorted() {
        return service.getAllSorted();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDTO create(@Valid @RequestBody TaskRequestDTO dto) {
        return service.create(dto);
    }
    @PutMapping("/{id}")
    public TaskResponseDTO update(@PathVariable Long id,
                                  @Valid @RequestBody TaskRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


}
