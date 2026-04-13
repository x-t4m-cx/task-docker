package com.example.tasks.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound() {
        ResourseNotFoundException ex = new ResourseNotFoundException("Not found");

        ResponseEntity<?> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(Map.of("error", "Not found"), response.getBody());
    }

    @Test
    void handleGeneral() {
        ResponseEntity<?> response = handler.handleGeneral();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Map.of("error", "Internal server error"), response.getBody());
    }

    @Test
    void handleValidation() {
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "obj");

        bindingResult.addError(new FieldError("obj", "name", "Name is required"));

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<?> response = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("Name is required", body.get("name"));
    }
}