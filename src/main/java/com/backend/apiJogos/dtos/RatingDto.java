package com.backend.apiJogos.dtos;
 
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDto {

 private Integer nota;

 private UUID userGameId;


}
