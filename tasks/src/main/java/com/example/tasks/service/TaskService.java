package com.example.tasks.service;

import com.example.tasks.dto.TaskRequestDTO;
import com.example.tasks.dto.TaskResponseDTO;
import com.example.tasks.exception.ResourseNotFoundException;
import com.example.tasks.mapper.TaskMapper;
import com.example.tasks.model.Task;
import com.example.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public List<TaskResponseDTO> getAll(){
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
    public TaskResponseDTO getById(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(()-> new ResourseNotFoundException("Task not found"));
        return mapper.toDTO(task);
    }
    public TaskResponseDTO create(TaskRequestDTO dto){
        Task saved = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public TaskResponseDTO update(Long id, TaskRequestDTO dto){
        Task task = repository.findById(id)
                .orElseThrow(
                        () -> new ResourseNotFoundException("Task not found")
                );
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());

        return mapper.toDTO(repository.save(task));
    }

    public void delete(Long id){
        if (!repository.existsById(id)) {
            throw new ResourseNotFoundException("Task not found");
        }
        repository.deleteById(id);
    }
    public List<TaskResponseDTO> getAllSorted() {
        return repository.findAllByOrderByPriorityAscCreatedAtAsc()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
