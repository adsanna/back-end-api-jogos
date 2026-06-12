package com.backend.apiJogos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.backend.apiJogos.dtos.UserGameDto;
import com.backend.apiJogos.services.interfaces.UserGameService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-games")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserGameController {

  private final UserGameService userGameService;

  @PostMapping
  public ResponseEntity<?> criar(
      @RequestBody @Valid UserGameDto dto,
      BindingResult bd,
      @AuthenticationPrincipal Jwt jwt) {

    if (bd.hasErrors()) {
      return ResponseEntity.badRequest().body(bd.getAllErrors());
    }

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(userGameService.criar(dto, jwt));
  }

  @GetMapping
  public ResponseEntity<List<UserGameDto>> listar(
      @AuthenticationPrincipal Jwt jwt) {

    return ResponseEntity.ok(
        userGameService.listar(jwt));
  }

  @GetMapping("/buscar-id/{id}")
  public ResponseEntity<UserGameDto> buscarPorId(
      @PathVariable Long id,
      @AuthenticationPrincipal Jwt jwt) {

    return ResponseEntity.ok(
        userGameService.buscarPorId(id, jwt));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editar(
      @PathVariable Long id,
      @RequestBody @Valid UserGameDto dto,
      BindingResult bd,
      @AuthenticationPrincipal Jwt jwt) {

    if (bd.hasErrors()) {
      return ResponseEntity.badRequest().body(bd.getAllErrors());
    }

    return ResponseEntity.ok(
        userGameService.editar(id, dto, jwt));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(
      @PathVariable Long id,
      @AuthenticationPrincipal Jwt jwt) {

    userGameService.deletar(id, jwt);

    return ResponseEntity.noContent().build();
  }
}
