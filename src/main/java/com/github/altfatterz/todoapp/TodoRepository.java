package com.github.altfatterz.todoapp;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TodoRepository extends ListCrudRepository<Todo, Long> {

    @Query("SELECT * FROM todo WHERE completed = false")
    List<Todo> findAllActiveTodos();     // This method will return todos where completed is false
}