package com.keldorn.todocorejavaspringsolution.domain.entity;

import com.keldorn.todocorejavaspringsolution.domain.enums.Priority;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long todoId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "completed")
    private boolean completed;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "priority")
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Todo(String title, String description) {
        this(title, description, LocalDateTime.now().plusDays(1), false, Priority.LOW);
    }


    public Todo(String title, String description, LocalDateTime dueDate, boolean completed, Priority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.priority = priority;
    }
}
