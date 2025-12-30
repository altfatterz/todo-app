package com.github.altfatterz.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoRepository repository;

    @Test
    void shouldReturnIndexViewWithActiveTodos() throws Exception {
        // Arrange
        Todo todo = new Todo(1L, "Test Task", false, LocalDateTime.now());
        when(repository.findByCompletedFalse()).thenReturn(List.of(todo));

        // Act & Assert
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("todos"))
                .andExpect(model().attribute("todos", hasSize(1)));
    }

    @Test
    void shouldAddTodoAndReturnFragment() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/todos")
                        .param("task", "New Task"))
                .andExpect(status().isOk())
                // Verifies that only the specific Thymeleaf fragment is returned
                .andExpect(view().name("index :: todo-list"))
                .andExpect(model().attributeExists("todos"));

        // Verify the repository actually saved a new Todo
        verify(repository, times(1)).save(any(Todo.class));
    }

    @Test
    void shouldMarkTodoAsComplete() throws Exception {
        // Arrange
        Todo existingTodo = new Todo(1L, "Task", false, LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(existingTodo));

        // Act & Assert
        mockMvc.perform(post("/todos/1/complete"))
                .andExpect(status().isOk())
                .andExpect(view().name("index :: todo-list"));

        verify(repository).save(argThat(Todo::completed));
    }

    @Test
    void shouldDeleteTodo() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isOk());

        verify(repository).deleteById(1L);
    }

}
