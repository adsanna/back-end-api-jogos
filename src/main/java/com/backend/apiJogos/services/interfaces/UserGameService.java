package com.backend.apiJogos.services.interfaces;

import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;

import com.backend.apiJogos.dtos.UserGameDto;

public interface UserGameService {

    UserGameDto criar(UserGameDto dto, Jwt jwt);

    List<UserGameDto> listar(Jwt jwt);

    UserGameDto buscarPorId(Long id, Jwt jwt);

    UserGameDto editar(Long id, UserGameDto dto, Jwt jwt);

    void deletar(Long id, Jwt jwt);
}
