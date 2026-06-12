package com.backend.apiJogos.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.backend.apiJogos.config.security.UserContext;
import com.backend.apiJogos.dtos.GameDto;
import com.backend.apiJogos.exceptionHandler.exceptions.GameNotFoundException;
import com.backend.apiJogos.models.Game;
import com.backend.apiJogos.repositorys.GameRepository;
import com.backend.apiJogos.services.interfaces.GameService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

  private final GameRepository gameRepository;
  private final UserContext userContext;

  @Override
  public GameDto criar(GameDto gameDto, Jwt jwt) {
    userContext.validarAdmin(jwt);
    Game game = new Game(
        gameDto.getNome(),
        gameDto.getGenero());
    Game salvo = gameRepository.save(game);
    return new GameDto(
        salvo.getId(),
        salvo.getNome(),
        salvo.getGenero());
  }

  @Override
  public List<GameDto> listar() {
    return gameRepository.findAll()
        .stream()
        .map(game -> new GameDto(
            game.getId(),
            game.getNome(),
            game.getGenero()))
        .collect(Collectors.toList());
  }

  @Override
  public GameDto buscarPorId(Long id) {
    Game game = gameRepository.findById(id)
        .orElseThrow(GameNotFoundException::new);
    return new GameDto(
        game.getId(),
        game.getNome(),
        game.getGenero());
  }

  @Override
  public void deletar(Long id, Jwt jwt) {
    userContext.validarAdmin(jwt);
    Game game = gameRepository.findById(id)
        .orElseThrow(GameNotFoundException::new);
    gameRepository.delete(game);
  }

  @Override
  public GameDto editar(Long id, GameDto gameDto, Jwt jwt) {
    userContext.validarAdmin(jwt);
    Game game = gameRepository.findById(id)
        .orElseThrow(GameNotFoundException::new);
    game.setNome(gameDto.getNome());
    game.setGenero(gameDto.getGenero());
    Game atualizado = gameRepository.save(game);
    return new GameDto(
        atualizado.getId(),
        atualizado.getNome(),
        atualizado.getGenero());
  }

  @Override
  public List<GameDto> buscarPorNome(String nome) {
    return gameRepository
        .findByNomeContainingIgnoreCase(nome)
        .stream()
        .map(game -> new GameDto(
            game.getId(),
            game.getNome(),
            game.getGenero()))
        .collect(Collectors.toList());
  }
}
