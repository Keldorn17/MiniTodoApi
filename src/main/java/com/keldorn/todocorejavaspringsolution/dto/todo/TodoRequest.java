package com.keldorn.todocorejavaspringsolution.dto.todo;

import com.keldorn.todocorejavaspringsolution.domain.enums.Priority;

import java.time.LocalDateTime;

public record TodoRequest(String title, String description, LocalDateTime dueDate, Boolean completed, Priority priority) {
}
