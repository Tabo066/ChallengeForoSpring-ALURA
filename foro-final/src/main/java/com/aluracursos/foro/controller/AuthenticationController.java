package com.aluracursos.foro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.aluracursos.foro.domain.user.User;
import com.aluracursos.foro.infra.security.AuthenticationRequest;
import com.aluracursos.foro.infra.security.AuthenticationResponse;
import com.aluracursos.foro.infra.security.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;

    @PostMapping
    public AuthenticationResponse login(@RequestBody @Valid AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.username(), request.password());

        Authentication auth = authManager.authenticate(authToken);

        var user = (User) auth.getPrincipal();
        String jwt = jwtUtil.generateToken(user);

        return new AuthenticationResponse(jwt);
    }
}