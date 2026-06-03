package com.backend.apiJogos.config.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.backend.apiJogos.exceptionHandler.exceptions.AccessDeniedException;
import com.backend.apiJogos.exceptionHandler.exceptions.UserNotFoundException;
import com.backend.apiJogos.models.Role;
import com.backend.apiJogos.models.User;
import com.backend.apiJogos.repositorys.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserContext {

    private final UserRepository userRepository;

    public User getUser(Jwt jwt) {

        return userRepository
                .findBySupabaseUserId(jwt.getSubject())
                .orElseThrow(UserNotFoundException::new);
    }

    public void validarAdmin(Jwt jwt) {

        User user = getUser(jwt);

        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException();
        }
    }
}
