package com.aluracursos.foro.infra.security;


import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank String username,
        @NotBlank String password
) {}
