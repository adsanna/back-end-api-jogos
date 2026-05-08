package com.backend.apiJogos.repositorys;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.apiJogos.models.UserGame;
import com.backend.apiJogos.models.Status;

@Repository
public interface UserGameRepository extends JpaRepository<UserGame, UUID> {

    Optional<UserGame> findTopByUserIdAndGameIdOrderByNumeroRunDesc(UUID userId, UUID gameId);

    boolean existsByUserIdAndGameIdAndStatus(UUID userId, UUID gameId, Status status);
}
