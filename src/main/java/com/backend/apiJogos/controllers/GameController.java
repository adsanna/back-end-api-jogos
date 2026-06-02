package com.backend.apiJogos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.backend.apiJogos.dtos.GameDto;
import com.backend.apiJogos.services.interfaces.GameService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/games")
public class GameController {

  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @PostMapping
  public ResponseEntity<?> criar(
      @RequestBody @Valid GameDto gameDto,
      BindingResult bd,
      @AuthenticationPrincipal Jwt jwt) {

    if (bd.hasErrors()) {
      return ResponseEntity.badRequest().body(bd.getAllErrors());
    }

    GameDto gameSalvo = gameService.criar(gameDto, jwt);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(gameSalvo);
  }

  @GetMapping
  public ResponseEntity<List<GameDto>> listar() {

    return ResponseEntity.ok(
        gameService.listar());
  }

  @GetMapping("/buscar-id/{id}")
  public ResponseEntity<GameDto> buscarPorId(
      @PathVariable Long id) {

    return ResponseEntity.ok(
        gameService.buscarPorId(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(
      @PathVariable Long id,
      @AuthenticationPrincipal Jwt jwt) {

    gameService.deletar(id, jwt);

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editar(
      @RequestBody @Valid GameDto gameDto,
      BindingResult br,
      @PathVariable Long id,
      @AuthenticationPrincipal Jwt jwt) {

    if (br.hasErrors()) {
      return ResponseEntity.badRequest().body(br.getAllErrors());
    }

    return ResponseEntity.ok(
        gameService.editar(id, gameDto, jwt));
  }

  @GetMapping("/buscar-nome/{nome}")
  public ResponseEntity<List<GameDto>> buscarPorNome(
      @PathVariable String nome) {

    return ResponseEntity.ok(
        gameService.buscarPorNome(nome));
  }
}
