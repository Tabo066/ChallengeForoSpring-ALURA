package com.aluracursos.foro.infra.security;

import com.aluracursos.foro.domain.user.User;
import lombok.RequiredArgsConstructor;
import com.aluracursos.foro.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    public User createUser(String name, String email, String password, String role) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(name, email, encodedPassword, role);
        return repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
