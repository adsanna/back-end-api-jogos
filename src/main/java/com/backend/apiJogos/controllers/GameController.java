package com.backend.apiJogos.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.backend.apiJogos.dtos.GameDto;
import com.backend.apiJogos.services.impls.GameServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameServiceImpl gameServiceImpl;

    public GameController(GameServiceImpl gameServiceImpl){
        this.gameServiceImpl = gameServiceImpl;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid GameDto gameDto, BindingResult bd){
        if(bd.hasErrors()){
            return ResponseEntity.badRequest().body(bd.getAllErrors());
        }
        GameDto gameSalvo = gameServiceImpl.criar(gameDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameSalvo);
    }

    @GetMapping
    public ResponseEntity<List<GameDto>> listar(){
        List<GameDto> gameDtos = gameServiceImpl.listar();
        return ResponseEntity.ok(gameDtos);
    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id){
       GameDto gameDto = gameServiceImpl.buscarPorId(id);
       return ResponseEntity.ok().body(gameDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable UUID id){
        gameServiceImpl.deletar(id);
        return ResponseEntity.noContent().build();
    }

  @PutMapping("/{id}")
  public ResponseEntity<?> editar(@RequestBody @Valid GameDto gameDto, BindingResult br, @PathVariable UUID id){
    if(br.hasErrors()){
      return ResponseEntity.badRequest().body(br.getAllErrors());
    }
    GameDto gameEditado = gameServiceImpl.editar(id, gameDto);
    return ResponseEntity.status(HttpStatus.OK).body(gameEditado);
  }

  @GetMapping("/buscar-nome/{nome}")
  public ResponseEntity<List<GameDto>> buscarPorNome(@PathVariable String nome){
    List<GameDto> lista = gameServiceImpl.buscarPorNome(nome);
    return ResponseEntity.ok(lista);

  }








}
