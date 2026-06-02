package com.backend.apiJogos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.backend.apiJogos.dtos.RoleUpdateDto;
import com.backend.apiJogos.dtos.UserPublicDto;
import com.backend.apiJogos.dtos.UserDto;
import com.backend.apiJogos.services.interfaces.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public ResponseEntity<UserDto> me(@AuthenticationPrincipal Jwt jwt) {
    return ResponseEntity.ok(userService.me(jwt));
  }

  @PutMapping("/me")
  public ResponseEntity<UserDto> atualizar(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid UserDto dto) {
    return ResponseEntity.ok(
        userService.atualizarMeuPerfil(jwt, dto));
  }

  @DeleteMapping("/me")
  public ResponseEntity<Void> deletar(@AuthenticationPrincipal Jwt jwt) {
    userService.deletarMinhaConta(jwt);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/role")
  public ResponseEntity<UserDto> alterarRole(@PathVariable Long id, @RequestBody @Valid RoleUpdateDto dto,
      @AuthenticationPrincipal Jwt jwt) {
    return ResponseEntity.ok(
        userService.alterarRole(id, dto, jwt));
  }

  @GetMapping
  public ResponseEntity<java.util.List<UserDto>> listar(@AuthenticationPrincipal Jwt jwt) {
    return ResponseEntity.ok(
        userService.listar(jwt));
  }

  @GetMapping("/buscar-nome/{nome}")
  public ResponseEntity<java.util.List<UserPublicDto>> buscarPorNome(@PathVariable String nome,
      @AuthenticationPrincipal Jwt jwt) {
    return ResponseEntity.ok(userService.buscarPorNome(nome, jwt));
  }

}
