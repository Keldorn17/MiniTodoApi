package com.keldorn.todocorejavaspringsolution.dto.user;

import jakarta.validation.constraints.Email;

public record UserRequest(@Email String email, String name) {
}
