package com.backend.apiJogos.services.impls;

import org.springframework.stereotype.Service;

import com.backend.apiJogos.services.interfaces.RatingService;

import com.backend.apiJogos.repositorys.RatingRepository;
import com.backend.apiJogos.repositorys.UserGameRepository;

import com.backend.apiJogos.dtos.RatingDto;
import com.backend.apiJogos.models.UserGame;
import com.backend.apiJogos.models.Status;
import com.backend.apiJogos.models.Rating;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    private final UserGameRepository userGameRepository;
@Override
 public void criar(RatingDto ratingDto) {

    UserGame userGame = userGameRepository.findById(ratingDto.getUserGameId())
            .orElseThrow();

    if (ratingDto.getNota() < 0 || ratingDto.getNota() > 10) {
        throw new RuntimeException("A nota deve estar entre 0 e 10");
    }

    if (userGame.getStatus() != Status.FINALIZADO) {
        throw new RuntimeException("Só é possível avaliar jogos finalizados");
    }
    if (ratingRepository.existsByUserGame(userGame)) {
    throw new RuntimeException("Esse jogo já foi avaliado");
}
Rating rating = new Rating();
rating.setNota(ratingDto.getNota());
rating.setUserGame(userGame);

ratingRepository.save(rating);
}
}
