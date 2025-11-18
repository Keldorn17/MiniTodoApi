package com.keldorn.todocorejavaspringsolution.dto.todo;

import java.time.LocalDateTime;

public record TodoResponse(Long todoId, String title, String description, LocalDateTime dueDate,
                           boolean completed, int priority, Long userId) {
}
