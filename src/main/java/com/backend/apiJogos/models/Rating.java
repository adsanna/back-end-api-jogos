package com.backend.apiJogos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_rating")
public class Rating {

 @Id
 @GeneratedValue(strategy = GenerationType.UUID)
 private UUID id;

 private Integer nota;

@ManyToOne
@JoinColumn(name= "user_game_id")
private UserGame userGame;

}
