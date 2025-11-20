package com.keldorn.todocorejavaspringsolution.service;

import com.keldorn.todocorejavaspringsolution.domain.entity.User;
import com.keldorn.todocorejavaspringsolution.domain.enums.Role;
import com.keldorn.todocorejavaspringsolution.dto.auth.AuthRequest;
import com.keldorn.todocorejavaspringsolution.dto.auth.AuthResponse;
import com.keldorn.todocorejavaspringsolution.dto.auth.RegisterRequest;
import com.keldorn.todocorejavaspringsolution.exception.EmailIsTakenException;
import com.keldorn.todocorejavaspringsolution.exception.UsernameIsTakenException;
import com.keldorn.todocorejavaspringsolution.repository.UserRepository;
import com.keldorn.todocorejavaspringsolution.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        log.debug("Creating user.");
        isUsernameTaken(request.getUsername());
        isEmailTaken(request.getEmail());
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);
        return getResponse(user);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return getResponse(user);
    }

    private AuthResponse getResponse(User user) {
        var jwtToken = jwtService.generateToken(user.getUsername());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void isUsernameTaken(String username) {
        log.debug("Checking if username is taken");
        if (userRepository.getUserUsernameCount(username) != 0) {
            throw new UsernameIsTakenException("Username is taken.");
        }
    }

    private void isEmailTaken(String email) {
        log.debug("Checking if email is taken");
        if (userRepository.getUserEmailCount(email) != 0) {
            throw new EmailIsTakenException("Email is taken.");
        }
    }
}
