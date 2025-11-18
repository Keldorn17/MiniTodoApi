package com.keldorn.todocorejavaspringsolution.service;

import com.keldorn.todocorejavaspringsolution.domain.entity.Todo;
import com.keldorn.todocorejavaspringsolution.domain.entity.User;
import com.keldorn.todocorejavaspringsolution.dto.todo.TodoRequest;
import com.keldorn.todocorejavaspringsolution.dto.todo.TodoResponse;
import com.keldorn.todocorejavaspringsolution.exception.TodoNotFoundException;
import com.keldorn.todocorejavaspringsolution.exception.UserNotFoundException;
import com.keldorn.todocorejavaspringsolution.mapper.TodoMapper;
import com.keldorn.todocorejavaspringsolution.repository.TodoRepository;
import com.keldorn.todocorejavaspringsolution.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repository;
    private final UserRepository userRepository;
    private final TodoMapper mapper;

    public TodoResponse findById(Long userId, Long todoId) {
        log.debug("Finding todo by todoId={} for userId={}",todoId, userId);
        verifyOwner(userId, todoId);
        return mapper.toResponse(findByIdOrThrow(todoId));
    }

    private Todo findByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found by id: " + id));
    }

    public List<TodoResponse> findAllForUser(Long userId) {
        log.debug("Finding todos for userId={}", userId);
        return repository.findAllForUser(userId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public TodoResponse create(Long userId, TodoRequest request) {
        log.debug("Creating todo for userId={}", userId);
        Todo todo = mapper.toEntity(request);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));
        todo.setUser(user);

        return mapper.toResponse(repository.save(todo));
    }

    public TodoResponse update(Long userId, Long todoId, TodoRequest request) {
        log.debug("Updating todoId={} for userId={}", todoId, userId);
        verifyOwner(userId, todoId);
        Todo todo = findByIdOrThrow(todoId);
        todo.setCompleted(request.completed());
        todo.setTitle(request.title());
        todo.setDescription(request.description());
        todo.setDueDate(request.dueDate());
        todo.setPriority(request.priority());

        return mapper.toResponse(repository.save(todo));
    }

    public TodoResponse patch(Long userId, Long todoId, TodoRequest request) {
        log.debug("Patching todoId={} for userId={}", todoId, userId);
        verifyOwner(userId, todoId);
        Todo todo = findByIdOrThrow(todoId);
        if (request.completed() != null) todo.setCompleted(request.completed());
        if (request.title() != null) todo.setTitle(request.title());
        if (request.description() != null) todo.setDescription(request.description());
        if (request.dueDate() != null) todo.setDueDate(request.dueDate());
        if (request.priority() != null) todo.setPriority(request.priority());

        return mapper.toResponse(repository.save(todo));
    }

    public void deleteById(Long userId, Long todoId) {
        log.debug("Deleting todoId={} for userId={}", todoId, userId);
        verifyOwner(userId, todoId);
        findByIdOrThrow(todoId);
        repository.deleteById(todoId);
    }

    @SneakyThrows
    private void verifyOwner(Long userId, Long todoId) {
        log.debug("Verifying ownership. todoId={}, userId={}", todoId, userId);
        Todo todo = findByIdOrThrow(todoId);
        if (!todo.getUser().getUserId().equals(userId)) {
            log.warn("User {} attempted forbidden access to todo {}", userId, todoId);
            throw new AccessDeniedException("You do not own this todo");
        }
    }
}
