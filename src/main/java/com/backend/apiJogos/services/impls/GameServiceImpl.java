package com.backend.apiJogos.services.impls;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.apiJogos.dtos.GameDto;
import com.backend.apiJogos.models.Game;
import com.backend.apiJogos.repositorys.GameRepository;
import com.backend.apiJogos.services.interfaces.GameService;

import lombok.RequiredArgsConstructor;
import com.backend.apiJogos.exceptionHandler.exceptions.GameNotFoundException;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

@Override
public GameDto criar (GameDto gameDto){
    Game game = new Game(gameDto.getNome(), gameDto.getGenero());
    
    Game salvo = gameRepository.save(game);

    return new GameDto(salvo.getId(), salvo.getNome(), salvo.getGenero());
}
@Override
public List<GameDto> listar(){
 return gameRepository.findAll()
   .stream()
   .map(game -> new GameDto(game.getId(), game.getNome(), game.getGenero()))
   .collect(Collectors.toList());
}
@Override
public GameDto buscarPorId(UUID id){
    Game game = gameRepository.findById(id)
           .orElseThrow(() -> new GameNotFoundException());

    return new GameDto(game.getId(), game.getNome(), game.getGenero());
}
@Override
public void deletar(UUID id){
    gameRepository.deleteById(id);
}
@Override
public GameDto editar(UUID id, GameDto gameDto){
    Game game = gameRepository.findById(id)
           .orElseThrow(() -> new GameNotFoundException());
        
        game.setNome(gameDto.getNome());
        game.setGenero(gameDto.getGenero());


        Game atualizado = gameRepository.save(game);

    return new GameDto(atualizado.getId(),atualizado.getNome(), atualizado.getGenero());
}
@Override
public List<GameDto> buscarPorNome(String nome){
    return gameRepository.findByNomeContainingIgnoreCase(nome)
         .stream ()
         .map(game -> new GameDto(game.getId(), game.getNome(),game.getGenero()))
         .collect(Collectors.toList());
}
}
