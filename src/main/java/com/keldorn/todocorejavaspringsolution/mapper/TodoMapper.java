package com.keldorn.todocorejavaspringsolution.mapper;

import com.keldorn.todocorejavaspringsolution.domain.entity.Todo;
import com.keldorn.todocorejavaspringsolution.dto.todo.TodoRequest;
import com.keldorn.todocorejavaspringsolution.dto.todo.TodoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    @Mapping(target = "userId", source = "user.userId")
    TodoResponse toResponse(Todo todo);
    @Mapping(target = "user.userId", source = "userId")
    Todo toEntity(TodoRequest request);
}
