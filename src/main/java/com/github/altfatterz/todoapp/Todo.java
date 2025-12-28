package com.github.altfatterz.todoapp;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

public record Todo(@Id Long id, String task, boolean completed, LocalDateTime completedDate) {}