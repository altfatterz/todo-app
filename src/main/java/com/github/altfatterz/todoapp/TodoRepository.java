package com.github.altfatterz.todoapp;

import org.springframework.data.repository.ListCrudRepository;

public interface TodoRepository extends ListCrudRepository<Todo, Long> {}