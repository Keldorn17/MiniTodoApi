package com.keldorn.todocorejavaspringsolution.controller;

import com.keldorn.todocorejavaspringsolution.annotation.CurrentUser;
import com.keldorn.todocorejavaspringsolution.dto.user.UserDetailedResponse;
import com.keldorn.todocorejavaspringsolution.dto.user.UserRequest;
import com.keldorn.todocorejavaspringsolution.dto.user.UserResponse;
import com.keldorn.todocorejavaspringsolution.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v2/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(response.userId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser Long userId) {
        log.warn("{}", userId);
        return ResponseEntity.ok(userService.getCurrentUser(userId));
    }

    @GetMapping("/details")
    public ResponseEntity<UserDetailedResponse> getDetailedUser(@CurrentUser Long userId) {
        return ResponseEntity.ok(userService.getUserTodos(userId));
    }
}
