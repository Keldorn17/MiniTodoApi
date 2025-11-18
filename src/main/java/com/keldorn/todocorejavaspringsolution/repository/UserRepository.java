package com.keldorn.todocorejavaspringsolution.repository;

import com.keldorn.todocorejavaspringsolution.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User WHERE userId = ?1")
    User getUserTodos(Long userId);

    @Query("FROM User WHERE username = ?1")
    Optional<User> findByUsername(String username);

    @Query("SELECT COUNT(*) FROM User u WHERE u.username = ?1")
    Long getUserUsernameCount(String username);

    @Query("SELECT COUNT(*) FROM User u WHERE u.email = ?1")
    Long getUserEmailCount(String email);
}
