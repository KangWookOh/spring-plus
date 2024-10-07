package org.example.expert.domain.manager.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor

public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime eventTime;

    private String requestType;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @Builder
    public Log(Long id, LocalDateTime eventTime, String requestType, String description, User user, Todo todo) {
        this.id = id;
        this.eventTime = eventTime;
        this.requestType = requestType;
        this.description = description;
        this.user = user;
        this.todo = todo;
    }
}
