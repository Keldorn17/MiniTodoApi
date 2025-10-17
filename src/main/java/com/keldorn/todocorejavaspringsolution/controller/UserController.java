package com.keldorn.todocorejavaspringsolution.controller;

import com.keldorn.todocorejavaspringsolution.dto.user.UserDetailedResponse;
import com.keldorn.todocorejavaspringsolution.dto.user.UserRequest;
import com.keldorn.todocorejavaspringsolution.dto.user.UserResponse;
import com.keldorn.todocorejavaspringsolution.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping
    public ResponseEntity<UserResponse> postUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.create(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(response.userId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> putUser(@PathVariable int userId, @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.update(userId, request));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> patchUser(@PathVariable int userId, @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.patch(userId, request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/details")
    public ResponseEntity<UserDetailedResponse> getDetailedUser(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getUserTodos(userId));
    }
}
