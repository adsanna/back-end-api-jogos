package com.backend.apiJogos.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user_game", uniqueConstraints = @UniqueConstraint(columnNames = {
    "supabase_user_id",
    "game_id",
    "numero_run"
}))
public class UserGame {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String supabaseUserId;

  @ManyToOne
  @JoinColumn(name = "game_id", nullable = false)
  private Game game;

  @Column(nullable = false)
  private Integer numeroRun = 1;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

  private BigDecimal horasJogadas;

  private LocalDate dataInicio;

  private LocalDate dataFim;

}
