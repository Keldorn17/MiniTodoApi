package com.keldorn.todocorejavaspringsolution.service;

import com.keldorn.todocorejavaspringsolution.domain.entity.User;
import com.keldorn.todocorejavaspringsolution.mapper.UserMapper;
import com.keldorn.todocorejavaspringsolution.dto.user.UserDetailedResponse;
import com.keldorn.todocorejavaspringsolution.dto.user.UserRequest;
import com.keldorn.todocorejavaspringsolution.dto.user.UserResponse;
import com.keldorn.todocorejavaspringsolution.exception.UserNotFoundException;
import com.keldorn.todocorejavaspringsolution.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    private User findEntityById(int userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));
    }

    @Override
    public UserResponse findById(int userId) {
        return mapper.toResponse(findEntityById(userId));
    }

    @Override
    public List<UserResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse create(UserRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    public UserResponse update(int userId, UserRequest request) {
        User user = findEntityById(userId);
        user.setName(request.name());
        user.setEmail(request.email());

        return mapper.toResponse(repository.save(user));
    }

    @Override
    public UserResponse patch(int userId, UserRequest request) {
        User user = findEntityById(userId);
        if (request.name() != null) user.setName(request.name());
        if (request.email() != null) user.setEmail(request.email());

        return mapper.toResponse(repository.save(user));
    }

    @Override
    public void deleteById(int userId) {
        findEntityById(userId);
        repository.deleteById(userId);
    }

    @Override
    public UserDetailedResponse getUserTodos(int userId) {
        return mapper.toDetailedResponse(repository.getUserTodos(userId));
    }
}
