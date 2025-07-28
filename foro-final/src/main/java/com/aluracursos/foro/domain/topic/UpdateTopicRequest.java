package com.aluracursos.foro.domain.topic;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTopicRequest(
        @NotNull Long id,
        @NotBlank String title,
        @NotBlank String message
) {}
