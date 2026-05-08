package com.backend.apiJogos.repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.apiJogos.models.Rating;
import com.backend.apiJogos.models.UserGame;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
 boolean existsByUserGame(UserGame userGame);
}
