package com.backend.apiJogos.dtos;

import com.backend.apiJogos.models.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "nome não pode ser nulo")
    private String nome;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String supabaseUserId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;
}
