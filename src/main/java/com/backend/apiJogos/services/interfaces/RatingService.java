package com.backend.apiJogos.services.interfaces;

import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;

import com.backend.apiJogos.dtos.RatingDto;

public interface RatingService {

    RatingDto criar(RatingDto ratingDto, Jwt jwt);

    List<RatingDto> listar(Jwt jwt);

    RatingDto buscarPorId(Long id, Jwt jwt);

    RatingDto editar(Long id, RatingDto dto, Jwt jwt);

    void deletar(Long id, Jwt jwt);
}
