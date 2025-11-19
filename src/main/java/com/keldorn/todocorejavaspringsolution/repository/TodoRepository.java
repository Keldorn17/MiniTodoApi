package com.keldorn.todocorejavaspringsolution.repository;

import com.keldorn.todocorejavaspringsolution.domain.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("FROM Todo t WHERE t.user.userId = ?1")
    List<Todo> findAllForUser(Long userId);
}
