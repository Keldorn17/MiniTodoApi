package com.keldorn.todocorejavaspringsolution.repository;

import com.keldorn.todocorejavaspringsolution.domain.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
