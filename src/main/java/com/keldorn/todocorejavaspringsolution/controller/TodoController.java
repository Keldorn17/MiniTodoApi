package com.keldorn.todocorejavaspringsolution.controller;

import com.keldorn.todocorejavaspringsolution.annotation.CurrentUser;
import com.keldorn.todocorejavaspringsolution.dto.todo.TodoRequest;
import com.keldorn.todocorejavaspringsolution.dto.todo.TodoResponse;
import com.keldorn.todocorejavaspringsolution.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v2/todos")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getTodosForUser(@CurrentUser Long userId) {
        return ResponseEntity.ok(todoService.findAllForUser(userId));
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponse> getTodoById(@CurrentUser Long userId, @PathVariable Long todoId) {
        return ResponseEntity.ok(todoService.findById(userId, todoId));
    }

    @PostMapping
    public ResponseEntity<TodoResponse> postTodo(@CurrentUser Long userId, @RequestBody TodoRequest request) {
        TodoResponse response = todoService.create(userId, request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(response.todoId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoResponse> patchTodo(@CurrentUser Long userId, @PathVariable Long todoId, @RequestBody TodoRequest request) {
        return ResponseEntity.ok(todoService.patch(userId, todoId, request));
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoResponse> putTodo(@CurrentUser Long userId, @PathVariable Long todoId, @RequestBody TodoRequest request) {
        return ResponseEntity.ok(todoService.update(userId, todoId, request));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@CurrentUser Long userId, @PathVariable Long todoId) {
        todoService.deleteById(userId, todoId);
        return ResponseEntity.noContent().build();
    }
}
