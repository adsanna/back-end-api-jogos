package com.backend.apiJogos.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.apiJogos.models.Status;
import com.backend.apiJogos.models.UserGame;

public interface UserGameRepository extends JpaRepository<UserGame, Long> {

  Optional<UserGame> findTopBySupabaseUserIdAndGameIdOrderByNumeroRunDesc(String supabaseUserId, Long gameId);

  boolean existsBySupabaseUserIdAndGameIdAndStatus(String supabaseUserId, Long gameId, Status status);

  List<UserGame> findBySupabaseUserId(String supabaseUserId);

  Optional<UserGame> findByIdAndSupabaseUserId(Long id, String supabaseUserId);
}
