package com.backend.apiJogos.dtos;

import com.backend.apiJogos.models.Role;

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
public class RoleUpdateDto {

    @NotNull(message = "role não pode ser nulo")
    private Role role;
}
