package com.backend.apiJogos.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.apiJogos.models.UserGame;
import com.backend.apiJogos.models.Status;

@Repository
public interface UserGameRepository extends JpaRepository<UserGame, Long> {

    Optional<UserGame> findTopByUserIdAndGameIdOrderByNumeroRunDesc(Long userId, Long gameId);

    boolean existsByUserIdAndGameIdAndStatus(Long userId, Long gameId, Status status);
}
