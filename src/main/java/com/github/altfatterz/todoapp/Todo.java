package com.github.altfatterz.todoapp;

import org.springframework.data.annotation.Id;

public record Todo(@Id Long id, String task, boolean completed) {}