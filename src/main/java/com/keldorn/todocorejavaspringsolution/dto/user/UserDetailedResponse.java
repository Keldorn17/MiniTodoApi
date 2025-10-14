package com.keldorn.todocorejavaspringsolution.dto.user;

import com.keldorn.todocorejavaspringsolution.dto.todo.TodoResponse;

import java.util.List;

public record UserDetailedResponse(int userId, String email, String name, List<TodoResponse> todos) {
}
