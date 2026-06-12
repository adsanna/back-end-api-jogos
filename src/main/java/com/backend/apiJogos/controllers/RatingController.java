package com.backend.apiJogos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.backend.apiJogos.dtos.RatingDto;
import com.backend.apiJogos.services.interfaces.RatingService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<?> criar(
            @RequestBody @Valid RatingDto ratingDto,
            BindingResult bd,
            @AuthenticationPrincipal Jwt jwt) {

        if (bd.hasErrors()) {
            return ResponseEntity.badRequest().body(bd.getAllErrors());
        }

        RatingDto ratingSalvo = ratingService.criar(ratingDto, jwt);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ratingSalvo);
    }

    @GetMapping
    public ResponseEntity<List<RatingDto>> listar(
            @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(
                ratingService.listar(jwt));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDto> buscarPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(
                ratingService.buscarPorId(id, jwt));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingDto> editar(
            @PathVariable Long id,
            @RequestBody @Valid RatingDto dto,
            @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(
                ratingService.editar(id, dto, jwt));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        ratingService.deletar(id, jwt);

        return ResponseEntity.noContent().build();
    }
}
