package com.backend.apiJogos.services.interfaces;

import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;

import com.backend.apiJogos.dtos.GameDto;

public interface GameService {

  GameDto criar(GameDto gameDto, Jwt jwt);

  List<GameDto> listar();

  GameDto buscarPorId(Long id);

  void deletar(Long id, Jwt jwt);

  GameDto editar(Long id, GameDto gameDto, Jwt jwt);

  List<GameDto> buscarPorNome(String nome);
}
