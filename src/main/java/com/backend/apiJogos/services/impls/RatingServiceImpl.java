package com.backend.apiJogos.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.backend.apiJogos.dtos.RatingDto;
import com.backend.apiJogos.exceptionHandler.exceptions.RatingException;
import com.backend.apiJogos.exceptionHandler.exceptions.RunNaoEncontradaException;
import com.backend.apiJogos.models.Rating;
import com.backend.apiJogos.models.Status;
import com.backend.apiJogos.models.UserGame;
import com.backend.apiJogos.repositorys.RatingRepository;
import com.backend.apiJogos.repositorys.UserGameRepository;
import com.backend.apiJogos.services.interfaces.RatingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

  private final RatingRepository ratingRepository;
  private final UserGameRepository userGameRepository;

  @Override
  public RatingDto criar(RatingDto ratingDto, Jwt jwt) {

    UserGame userGame = userGameRepository
        .findByIdAndSupabaseUserId(
            ratingDto.getUserGameId(),
            jwt.getSubject())
        .orElseThrow(() -> new RunNaoEncontradaException());

    if (ratingDto.getNota() < 0 || ratingDto.getNota() > 10) {
      throw new RatingException(
          "A nota deve estar entre 0 e 10");
    }

    if (userGame.getStatus() != Status.FINALIZADO) {
      throw new RatingException(
          "Só é possível avaliar jogos finalizados");
    }

    if (ratingRepository.existsByUserGame(userGame)) {
      throw new RatingException(
          "Esse jogo já foi avaliado");
    }

    Rating rating = new Rating();

    rating.setNota(ratingDto.getNota());
    rating.setUserGame(userGame);

    rating = ratingRepository.save(rating);

    return map(rating);
  }

  @Override
  public List<RatingDto> listar(Jwt jwt) {

    return ratingRepository
        .findByUserGameSupabaseUserId(
            jwt.getSubject())
        .stream()
        .map(this::map)
        .collect(Collectors.toList());
  }

  @Override
  public RatingDto buscarPorId(Long id, Jwt jwt) {

    Rating rating = ratingRepository
        .findByIdAndUserGameSupabaseUserId(
            id,
            jwt.getSubject())
        .orElseThrow(() -> new RatingException("Avaliação não encontrada"));

    return map(rating);
  }

  @Override
  public RatingDto editar(Long id, RatingDto dto, Jwt jwt) {

    Rating rating = ratingRepository
        .findByIdAndUserGameSupabaseUserId(
            id,
            jwt.getSubject())
        .orElseThrow(() -> new RatingException("Avaliação não encontrada"));

    if (dto.getNota() < 0 || dto.getNota() > 10) {
      throw new RatingException(
          "A nota deve estar entre 0 e 10");
    }

    rating.setNota(dto.getNota());

    return map(
        ratingRepository.save(rating));
  }

  @Override
  public void deletar(Long id, Jwt jwt) {

    Rating rating = ratingRepository
        .findByIdAndUserGameSupabaseUserId(
            id,
            jwt.getSubject())
        .orElseThrow(() -> new RatingException("Avaliação não encontrada"));

    ratingRepository.delete(rating);
  }

  private RatingDto map(Rating rating) {

    return new RatingDto(
        rating.getId(),
        rating.getNota(),
        rating.getUserGame().getId());
  }
}
