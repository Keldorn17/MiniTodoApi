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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repository;
    private final UserRepository userRepository;
    private final TodoMapper mapper;

    @Autowired
    public TodoServiceImpl(TodoRepository repository, UserRepository userRepository, TodoMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public TodoResponse findById(int todoId) {
        return mapper.toResponse(findEntityById(todoId));
    }

    private Todo findEntityById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found by id: " + id));
    }

    @Override
    public List<TodoResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public TodoResponse create(TodoRequest request) {
        Todo todo = mapper.toEntity(request);
        int userId = todo.getUser().getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));
        todo.setUser(user);

        return mapper.toResponse(repository.save(todo));
    }

    @Override
    public TodoResponse update(int todoId, TodoRequest request) {
        Todo todo = findEntityById(todoId);
        todo.setCompleted(request.completed());
        todo.setTitle(request.title());
        todo.setDescription(request.description());
        todo.setDueDate(request.dueDate());
        todo.setPriority(request.priority());

        return mapper.toResponse(repository.save(todo));
    }

    @Override
    public TodoResponse patch(int todoId, TodoRequest request) {
        Todo todo = findEntityById(todoId);
        if (request.completed() != null) todo.setCompleted(request.completed());
        if (request.title() != null) todo.setTitle(request.title());
        if (request.description() != null) todo.setDescription(request.description());
        if (request.dueDate() != null) todo.setDueDate(request.dueDate());
        if (request.priority() != null) todo.setPriority(request.priority());

        return mapper.toResponse(repository.save(todo));
    }

    @Override
    public void deleteById(int id) {
        findEntityById(id);
        repository.deleteById(id);
    }
}
