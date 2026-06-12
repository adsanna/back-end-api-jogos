package com.backend.apiJogos.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.apiJogos.models.Rating;
import com.backend.apiJogos.models.UserGame;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

  boolean existsByUserGame(UserGame userGame);

  List<Rating> findByUserGameSupabaseUserId(String supabaseUserId);

  Optional<Rating> findByIdAndUserGameSupabaseUserId(
      Long id,
      String supabaseUserId);
}
