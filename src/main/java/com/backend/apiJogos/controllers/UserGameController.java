package com.backend.apiJogos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.backend.apiJogos.dtos.UserGameDto;
import com.backend.apiJogos.services.impls.UserGameServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-games")
@RequiredArgsConstructor
public class UserGameController {

    private final UserGameServiceImpl userGameServiceImpl;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid UserGameDto userGameDto, BindingResult bd){
        if(bd.hasErrors()){
            return ResponseEntity.badRequest().body(bd.getAllErrors());
        }
        UserGameDto userGameSalvo = userGameServiceImpl.criar(userGameDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userGameSalvo);
    }

    @GetMapping
    public ResponseEntity<List<UserGameDto>> listar(){
        List<UserGameDto> userGameDtos = userGameServiceImpl.listar();
        return ResponseEntity.ok(userGameDtos);
    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
       UserGameDto userGameDto = userGameServiceImpl.buscarPorId(id);
       return ResponseEntity.ok().body(userGameDto);
    }

    @PutMapping("/{id}")
  public ResponseEntity<?> editar(@RequestBody @Valid UserGameDto userGameDto, BindingResult br, @PathVariable Long id){
    if(br.hasErrors()){
      return ResponseEntity.badRequest().body(br.getAllErrors());
    }
    UserGameDto userGameEditado = userGameServiceImpl.editar(id, userGameDto);
    return ResponseEntity.status(HttpStatus.OK).body(userGameEditado);
  }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        userGameServiceImpl.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
