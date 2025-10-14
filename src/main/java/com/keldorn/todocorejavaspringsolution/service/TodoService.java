package com.keldorn.todocorejavaspringsolution.service;

import com.keldorn.todocorejavaspringsolution.dto.todo.TodoRequest;
import com.keldorn.todocorejavaspringsolution.dto.todo.TodoResponse;

import java.util.List;

public interface TodoService {
    TodoResponse findById(int todoId);
    List<TodoResponse> findAll();
    TodoResponse create(TodoRequest request);
    TodoResponse update(int todoId, TodoRequest request);
    TodoResponse patch(int todoId, TodoRequest request);
    void deleteById(int id);
}
