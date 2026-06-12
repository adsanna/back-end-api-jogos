package com.backend.apiJogos.models;

import jakarta.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String supabaseUserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User(String nome, String supabaseUserId) {
        this.nome = nome;
        this.supabaseUserId = supabaseUserId;
        this.role = Role.USER;
    }
}
