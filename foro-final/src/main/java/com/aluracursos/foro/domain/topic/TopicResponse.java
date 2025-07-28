package com.aluracursos.foro.domain.topic;

import java.time.LocalDateTime;

public record TopicResponse(
        Long id,
        String title,
        String message,
        LocalDateTime creationDate,
        TopicStatus status,
        String authorName,
        String courseName
) {
    public static TopicResponse from(Topic topic) {
        return new TopicResponse(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.getStatus(),
                topic.getAuthor().getUsername(),
                topic.getCourse().getName()
        );
    }
}
