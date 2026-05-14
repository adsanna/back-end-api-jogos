package com.backend.apiJogos.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_usuario")
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@Column(nullable = false)
private String nome;

public User(String nome){
    this.nome = nome;
}
}
