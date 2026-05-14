package com.backend.apiJogos.services.interfaces;

import java.util.List;

import com.backend.apiJogos.dtos.UserGameDto;

public interface UserGameService {

    UserGameDto criar(UserGameDto dto);

    List<UserGameDto> listar();

    UserGameDto buscarPorId(Long id);

    UserGameDto editar(Long id, UserGameDto dto);

    void deletar(Long id);
}
