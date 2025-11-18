package com.keldorn.todocorejavaspringsolution.controller;

import com.keldorn.todocorejavaspringsolution.annotation.CurrentUser;
import com.keldorn.todocorejavaspringsolution.constant.ApiRoutes;
import com.keldorn.todocorejavaspringsolution.dto.todo.TodoRequest;
import com.keldorn.todocorejavaspringsolution.dto.todo.TodoResponse;
import com.keldorn.todocorejavaspringsolution.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(ApiRoutes.TODOS_URL)
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getTodosForUser(@CurrentUser Long userId) {
        log.info("GET {}: by userId={}", ApiRoutes.TODOS_URL, userId);
        return ResponseEntity.ok(todoService.findAllForUser(userId));
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponse> getTodoById(@CurrentUser Long userId, @PathVariable Long todoId) {
        log.info("GET {}/{}: by userId={}", ApiRoutes.TODOS_URL, todoId, userId);
        return ResponseEntity.ok(todoService.findById(userId, todoId));
    }

    @PostMapping
    public ResponseEntity<TodoResponse> postTodo(@CurrentUser Long userId, @RequestBody TodoRequest request) {
        log.info("POST {}: by userId={}", ApiRoutes.TODOS_URL, userId);
        TodoResponse response = todoService.create(userId, request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(response.todoId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoResponse> patchTodo(@CurrentUser Long userId, @PathVariable Long todoId, @RequestBody TodoRequest request) {
        log.info("PATCH {}/{}: by userId={}", ApiRoutes.TODOS_URL, todoId, userId);
        return ResponseEntity.ok(todoService.patch(userId, todoId, request));
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoResponse> putTodo(@CurrentUser Long userId, @PathVariable Long todoId, @RequestBody TodoRequest request) {
        log.info("PUT {}/{}: by userId={}", ApiRoutes.TODOS_URL, todoId, userId);
        return ResponseEntity.ok(todoService.update(userId, todoId, request));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@CurrentUser Long userId, @PathVariable Long todoId) {
        log.info("DELETE {}/{}: by userId={}", ApiRoutes.TODOS_URL, todoId, userId);
        todoService.deleteById(userId, todoId);
        return ResponseEntity.noContent().build();
    }
}
