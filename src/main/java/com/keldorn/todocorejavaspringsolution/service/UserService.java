package com.keldorn.todocorejavaspringsolution.service;

import com.keldorn.todocorejavaspringsolution.domain.entity.User;
import com.keldorn.todocorejavaspringsolution.dto.user.UserDetailedResponse;
import com.keldorn.todocorejavaspringsolution.dto.user.UserRequest;
import com.keldorn.todocorejavaspringsolution.dto.user.UserResponse;
import com.keldorn.todocorejavaspringsolution.exception.UserNotFoundException;
import com.keldorn.todocorejavaspringsolution.mapper.UserMapper;
import com.keldorn.todocorejavaspringsolution.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    private User findByIdOrThrow(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));
    }

    public UserResponse createUser(UserRequest request) {
        User user = mapper.toEntity(request);
        user.setPasswordHashed(passwordEncoder.encode(user.getPasswordHashed()));
        user.setRole("ROLE_USER");
        return mapper.toResponse(repository.save(user));
    }

    public UserDetailedResponse getUserTodos(Long userId) {
        return mapper.toDetailedResponse(repository.getUserTodos(userId));
    }

    public UserResponse getCurrentUser(Long userId) {
        return mapper.toResponse(findByIdOrThrow(userId));
    }

    public boolean isUsernameTaken(String username) {
        return repository.getUserUsernameCount(username) != 0;
    }

    public boolean isEmailTaken(String email) {
        return repository.getUserEmailCount(email) != 0;
    }
}
