package com.example.tasks.service;

import com.example.tasks.dto.TaskRequestDTO;
import com.example.tasks.dto.TaskResponseDTO;
import com.example.tasks.exception.ResourseNotFoundException;
import com.example.tasks.mapper.TaskMapper;
import com.example.tasks.model.Task;
import com.example.tasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private TaskMapper mapper;

    @InjectMocks
    private TaskService service;

    private Task task;
    private TaskRequestDTO requestDTO;
    private TaskResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test task");

        requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Test task");

        responseDTO = new TaskResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitle("Test task");
    }

    @Test
    void getAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(task));
        when(mapper.toDTO(task)).thenReturn(responseDTO);

        List<TaskResponseDTO> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("Test task", result.get(0).getTitle());
        verify(repository).findAll();
    }

    @Test
    void getById_shouldReturnTask() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(mapper.toDTO(task)).thenReturn(responseDTO);

        TaskResponseDTO result = service.getById(1L);

        assertEquals(1L, result.getId());
        verify(repository).findById(1L);
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourseNotFoundException.class,
                () -> service.getById(1L));
    }

    @Test
    void create_shouldSaveTask() {
        when(mapper.toEntity(requestDTO)).thenReturn(task);
        when(repository.save(task)).thenReturn(task);
        when(mapper.toDTO(task)).thenReturn(responseDTO);

        TaskResponseDTO result = service.create(requestDTO);

        assertEquals("Test task", result.getTitle());
        verify(repository).save(task);
    }

    @Test
    void update_shouldUpdateTask() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(repository.save(task)).thenReturn(task);
        when(mapper.toDTO(task)).thenReturn(responseDTO);

        TaskResponseDTO result = service.update(1L, requestDTO);

        assertEquals("Test task", result.getTitle());
        verify(repository).save(task);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourseNotFoundException.class,
                () -> service.update(1L, requestDTO));
    }

    @Test
    void delete_shouldDeleteTask() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void delete_shouldThrowException_whenNotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(ResourseNotFoundException.class,
                () -> service.delete(1L));
    }

    @Test
    void getAllSorted_shouldReturnSortedList() {
        when(repository.findAllByOrderByPriorityAscCreatedAtAsc())
                .thenReturn(List.of(task));
        when(mapper.toDTO(task)).thenReturn(responseDTO);

        List<TaskResponseDTO> result = service.getAllSorted();

        assertEquals(1, result.size());
        verify(repository).findAllByOrderByPriorityAscCreatedAtAsc();
    }
}