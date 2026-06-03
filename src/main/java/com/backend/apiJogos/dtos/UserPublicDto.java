package com.backend.apiJogos.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPublicDto {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  private String nome;
}
