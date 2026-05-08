package com.backend.apiJogos.repositorys;

import java.util.UUID;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.apiJogos.models.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
  List<Game> findByNomeContainingIgnoreCase(String nome);
}
