package com.aluracursos.foro.domain.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosTopic(@NotBlank String title,
                         @NotBlank String message,
                         @NotNull Long authorId,
                         @NotNull Long courseId) {

}
