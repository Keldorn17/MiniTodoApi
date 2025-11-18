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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repository;
    private final UserRepository userRepository;
    private final TodoMapper mapper;

    public TodoResponse findById(Long todoId) {
        return mapper.toResponse(findByIdOrThrow(todoId));
    }

    private Todo findByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found by id: " + id));
    }

    public List<TodoResponse> findAllForUser(Long userid) {
        return repository.findAllForUser(userid)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public TodoResponse create(TodoRequest request) {
        Todo todo = mapper.toEntity(request);
        Long userId = todo.getUser().getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));
        todo.setUser(user);

        return mapper.toResponse(repository.save(todo));
    }

    public TodoResponse update(Long todoId, TodoRequest request) {
        Todo todo = findByIdOrThrow(todoId);
        todo.setCompleted(request.completed());
        todo.setTitle(request.title());
        todo.setDescription(request.description());
        todo.setDueDate(request.dueDate());
        todo.setPriority(request.priority());

        return mapper.toResponse(repository.save(todo));
    }

    public TodoResponse patch(Long todoId, TodoRequest request) {
        Todo todo = findByIdOrThrow(todoId);
        if (request.completed() != null) todo.setCompleted(request.completed());
        if (request.title() != null) todo.setTitle(request.title());
        if (request.description() != null) todo.setDescription(request.description());
        if (request.dueDate() != null) todo.setDueDate(request.dueDate());
        if (request.priority() != null) todo.setPriority(request.priority());

        return mapper.toResponse(repository.save(todo));
    }

    public void deleteById(Long id) {
        findByIdOrThrow(id);
        repository.deleteById(id);
    }

}
