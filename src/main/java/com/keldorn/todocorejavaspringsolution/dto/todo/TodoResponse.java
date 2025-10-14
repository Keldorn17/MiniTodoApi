package com.keldorn.todocorejavaspringsolution.dto.todo;

import java.time.LocalDateTime;

public record TodoResponse(int todoId, String title, String description, LocalDateTime dueDate,
                           boolean completed, int priority, int userId) {
}
