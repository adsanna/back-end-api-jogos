package com.backend.apiJogos.repositorys;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.apiJogos.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNome(String nome);

    Optional<User> findBySupabaseUserId(String supabaseUserId);

    // List<User> findByNomeContaining(String nome);
    List<User> findByNomeContainingIgnoreCase(String nome);

}
