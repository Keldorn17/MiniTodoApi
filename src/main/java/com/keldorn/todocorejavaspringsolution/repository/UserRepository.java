package com.keldorn.todocorejavaspringsolution.repository;

import com.keldorn.todocorejavaspringsolution.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("FROM User WHERE userId = ?1")
    User getUserTodos(int userId);
}
