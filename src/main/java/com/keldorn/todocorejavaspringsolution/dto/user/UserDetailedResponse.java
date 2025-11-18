package com.keldorn.todocorejavaspringsolution.dto.user;

import com.keldorn.todocorejavaspringsolution.dto.todo.TodoResponse;

import java.util.List;

public record UserDetailedResponse(Long userId, String email, String username, List<TodoResponse> todos) {
}
