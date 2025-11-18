package com.keldorn.todocorejavaspringsolution.mapper;

import com.keldorn.todocorejavaspringsolution.domain.entity.User;
import com.keldorn.todocorejavaspringsolution.dto.user.UserDetailedResponse;
import com.keldorn.todocorejavaspringsolution.dto.user.UserRequest;
import com.keldorn.todocorejavaspringsolution.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
    @Mapping(target = "passwordHashed", source = "password")
    User toEntity(UserRequest request);
    @Mapping(target = "todos", source = "user.todos")
    UserDetailedResponse toDetailedResponse(User user);
}
