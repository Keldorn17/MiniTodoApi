package com.keldorn.todocorejavaspringsolution.service;

import com.keldorn.todocorejavaspringsolution.dto.user.UserDetailedResponse;
import com.keldorn.todocorejavaspringsolution.dto.user.UserRequest;
import com.keldorn.todocorejavaspringsolution.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse findById(int userId);
    List<UserResponse> findAll();
    UserResponse create(UserRequest request);
    UserResponse update(int userId, UserRequest request);
    UserResponse patch(int userId, UserRequest request);
    void deleteById(int userId);
    UserDetailedResponse getUserTodos(int userId);
}
