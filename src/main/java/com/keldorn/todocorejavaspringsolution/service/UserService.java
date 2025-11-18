package com.keldorn.todocorejavaspringsolution.service;

import com.keldorn.todocorejavaspringsolution.domain.entity.User;
import com.keldorn.todocorejavaspringsolution.dto.user.UserDetailedResponse;
import com.keldorn.todocorejavaspringsolution.dto.user.UserRequest;
import com.keldorn.todocorejavaspringsolution.dto.user.UserResponse;
import com.keldorn.todocorejavaspringsolution.exception.UserNotFoundException;
import com.keldorn.todocorejavaspringsolution.mapper.UserMapper;
import com.keldorn.todocorejavaspringsolution.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
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
        log.debug("Creating user.");
        User user = mapper.toEntity(request);
        user.setPasswordHashed(passwordEncoder.encode(user.getPasswordHashed()));
        user.setRole("ROLE_USER");
        return mapper.toResponse(repository.save(user));
    }

    public UserDetailedResponse getUserTodos(Long userId) {
        log.debug("Getting user todos for userId={}", userId);
        return mapper.toDetailedResponse(repository.getUserTodos(userId));
    }

    public UserResponse getCurrentUser(Long userId) {
        log.debug("Getting current user for userId={}", userId);
        return mapper.toResponse(findByIdOrThrow(userId));
    }

    public boolean isUsernameTaken(String username) {
        log.debug("Checking if username is taken");
        return repository.getUserUsernameCount(username) != 0;
    }

    public boolean isEmailTaken(String email) {
        log.debug("Checking if email is taken");
        return repository.getUserEmailCount(email) != 0;
    }
}
