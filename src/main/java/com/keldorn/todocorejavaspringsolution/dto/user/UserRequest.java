package com.keldorn.todocorejavaspringsolution.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String password;
}
