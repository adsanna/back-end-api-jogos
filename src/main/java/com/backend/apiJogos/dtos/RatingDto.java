package com.backend.apiJogos.dtos;
 

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RatingDto {

 @JsonProperty(access = JsonProperty.Access.READ_ONLY)
 private Long id;

 @NotNull(message = "nota não deve ser nula!")
 private Integer nota;

 @NotNull(message = "UserGameId não deve ser nulo!")
 private Long userGameId;


}
