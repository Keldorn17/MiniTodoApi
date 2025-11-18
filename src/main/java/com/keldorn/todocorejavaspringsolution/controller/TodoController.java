package com.keldorn.todocorejavaspringsolution.controller;

import com.keldorn.todocorejavaspringsolution.dto.todo.TodoRequest;
import com.keldorn.todocorejavaspringsolution.dto.todo.TodoResponse;
import com.keldorn.todocorejavaspringsolution.security.AppUserDetails;
import com.keldorn.todocorejavaspringsolution.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<List<TodoResponse>> getTodosForUser(Authentication authentication) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(todoService.findAllForUser(userDetails.getId()));
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long todoId) {
        return ResponseEntity.ok(todoService.findById(todoId));
    }

    @PostMapping
    public ResponseEntity<TodoResponse> postTodo(@RequestBody TodoRequest request) {
        TodoResponse response = todoService.create(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(response.todoId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoResponse> patchTodo(@PathVariable Long todoId, @RequestBody TodoRequest request) {
        return ResponseEntity.ok(todoService.patch(todoId, request));
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoResponse> putTodo(@PathVariable Long todoId, @RequestBody TodoRequest request) {
        return ResponseEntity.ok(todoService.update(todoId, request));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) {
        todoService.deleteById(todoId);
        return ResponseEntity.noContent().build();
    }
}
