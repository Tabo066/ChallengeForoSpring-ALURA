package com.aluracursos.foro.domain.topic;

import java.time.LocalDateTime;

public record TopicDetailResponse(String title, String menssage, LocalDateTime creationDate, TopicStatus status, String author, String course) {}
