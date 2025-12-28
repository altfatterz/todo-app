package com.github.altfatterz.todoapp;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TodoRepository extends ListCrudRepository<Todo, Long> {
    List<Todo> findByCompletedTrue();
    List<Todo> findByCompletedFalse();
}