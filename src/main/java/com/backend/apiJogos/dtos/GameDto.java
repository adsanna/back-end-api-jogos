package com.backend.apiJogos.dtos;


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
public class GameDto {
     @JsonProperty(access = JsonProperty.Access.READ_ONLY)
     private Long id;

     @NotNull(message = "O nome não pode ser nulo")
     private String nome;
    
     private String genero;

}
