package org.example.expert.domain.todo.dto.response;

import lombok.Getter;

@Getter
public class TodoSearchResponse {
    private final Long id;
    private final String title;
    private final long assigneeCount;  // 담당자 수
    private final long commentCount;

    public TodoSearchResponse(Long id, String title, long assigneeCount, long commentCount) {
        this.id = id;
        this.title = title;
        this.assigneeCount = assigneeCount;
        this.commentCount = commentCount;
    }
}
