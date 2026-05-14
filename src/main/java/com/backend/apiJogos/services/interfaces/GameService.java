package com.backend.apiJogos.services.interfaces;

import java.util.List;

import com.backend.apiJogos.dtos.GameDto;

public interface GameService {
    GameDto criar (GameDto gameDto);

    List<GameDto> listar();

    GameDto buscarPorId(Long id);

    void deletar(Long id);

    GameDto editar(Long id, GameDto gameDto);

    List<GameDto> buscarPorNome(String nome);

}
